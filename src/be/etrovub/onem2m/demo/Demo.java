package be.etrovub.onem2m.demo;

import be.etrovub.onem2m.http.client.HttpClient;
import be.etrovub.onem2m.http.server.RestHttpServer;

/**
 * In this Demo class containing the main function, we start the HTTP notification server with our Notification handler.
 * We create an HttpClient (which can be extended to allow client certificate or basic authentication) and run the client demo. 
 * @author Steffen Thielemans
 */
public class Demo {
	public final static int notificationServerPort = 4040;
	public final static String notificationServerPath = "http://localhost:" + notificationServerPort; 
	public final static String basePath = "http://localhost:8080/~/in-cse/in-name/";
	public final static String adminACP = "admin:admin";
	
	public static void main(String[] args) {
		
		RestHttpServer myServer = new RestHttpServer(notificationServerPort);
		HttpClient myHttpClient = new HttpClient();
		myServer.setNotificationHandler(new NotificationParser(myHttpClient));
		myServer.start();
		
		new DemoClient(myHttpClient);	
	}		
}
