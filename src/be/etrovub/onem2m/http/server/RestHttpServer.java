package be.etrovub.onem2m.http.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.Notification;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import be.etrovub.onem2m.util.Marshaller;

/**
 * This class creates a RESTful HTTP Server designed to be used for receiving oneM2M notifications. It uses an embedded Jetty 
 * web server and uses a slightly modified version of RestHttpServlet, the HTTP parser used within the OM2M CSE implementation. 
 * 
 * A server instance is created by calling this class' constructor with or without port. 
 * Notifications can be parsed by implementing the NotificationHandler interface and setting setNotificationHandler() accordingly. 
 * Start the server by calling the start() method.
 * 
 * @author Steffen Thielemans
 *
 */
public class RestHttpServer implements ServerRequestHandler {
    
	private static Log LOGGER = LogFactory.getLog(RestHttpServer.class);
	protected final static int DEFAULTPORT = 4040;

	protected Server server;
	private NotificationHandler notificationHandler; 
		
	public RestHttpServer() {
		this(DEFAULTPORT);
	}
	
	public RestHttpServer(int port) {
		server = new Server(port);
	    ServletContextHandler context = new ServletContextHandler();
	    context.setContextPath("/");
	    context.addServlet(new ServletHolder(new RestHttpServlet()),"/*");
	    server.setHandler(context);
	    
	    RestHttpServlet.setRequestHandler(this);
	    
	}
	
	
	/**
	 * Set this function with a NotificationHandler to parse received NotificationRequests. 
	 * Used in combination with the default ServerRequestHandler.
	 * @param notificationHandler
	 */
	public void setNotificationHandler(NotificationHandler notificationHandler) {
		this.notificationHandler = notificationHandler;
	}	
		
	public void start() {
		if(server != null) {
	        try {
	            server.start();
	//			server.join();		//Do not join, keep seperate main and server threads	
			} catch (Exception e) {
				LOGGER.error("Could not start oneM2M Server",e);
			}
		} else {
			LOGGER.error("Could not start oneM2M Server. No RestHttpServer object available.");
		}
	}
	
	public void stop() {
		if(server != null) {
			try {
				server.stop();
				server = null;
			} catch (Exception e) {
				LOGGER.error("Could not stop oneM2M Server",e);
			}
		}
	}
	
	/**
	 * Can be used instead of the default serverRequestHandler that only works with Notifications. 
	 * @param serverRequestHandler
	 */
	public void setRequestHandler(ServerRequestHandler serverRequestHandler) {
	    RestHttpServlet.setRequestHandler(serverRequestHandler);
	}
	
	/**
	 * This function parses an incoming oneM2M RequestPrimitive. Since we are not a oneM2M CSE but a oneM2M client these requests 
	 * will/should only be notifications. 
	 * In case we indeed receive a notification, we return ResponseStatusCode.OK, indicating succesful reception of the notification. 
	 * A notificationHandler can be configured to further parse the actual received notifications. 
	 */
	public ResponsePrimitive doRequest(RequestPrimitive request) {
		ResponsePrimitive response = new ResponsePrimitive();
		try {
			Object o = Marshaller.getMapper(request.getRequestContentType()).stringToObj((String) request.getContent()); //Unmarshall
			if(o instanceof Notification) {
				Notification n = (Notification)o;
				if(notificationHandler != null && n.getNotificationEvent() != null) {
					notificationHandler.parseNotification(n.getSubscriptionReference(), n.getNotificationEvent());
				}
				response.setResponseStatusCode(ResponseStatusCode.OK);
				return response;
			} else { 	//Unlikely event in which we do not receive a notification
				response.setResponseStatusCode(ResponseStatusCode.INTERNAL_SERVER_ERROR);
				return response;
			}
		} catch (Exception e) { //We received a request which cannot be unmarshalled.
			response.setResponseStatusCode(ResponseStatusCode.INTERNAL_SERVER_ERROR);
			return response;	
		}
	
	}
	
}
