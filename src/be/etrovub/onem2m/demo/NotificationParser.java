package be.etrovub.onem2m.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.Resource;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.resource.Notification.NotificationEvent;

import be.etrovub.onem2m.http.client.HttpClient;
import be.etrovub.onem2m.http.server.NotificationHandler;
import be.etrovub.onem2m.util.RqpUtil;

public class NotificationParser implements NotificationHandler{
	private static Log LOGGER = LogFactory.getLog(NotificationParser.class);
	private HttpClient httpClient;

	public NotificationParser(HttpClient httpClient) {
		this.httpClient = httpClient;
	}
	
	@Override
	public void parseNotification(String subscriptionReference, NotificationEvent event) {
		Resource resource = event.getRepresentation().getResource();
		int resourceStatus = event.getResourceStatus().intValue();  // See commons.constants.ResourceStatus
		// 1 = Child Created,   2 = Child Deleted,   3 = Updated,   4 = Deleted
		if(resource instanceof ContentInstance) {
			ContentInstance cin = (ContentInstance)resource;
			LOGGER.info("Sub: "+ subscriptionReference + " retrieved Content Instance with status " + resourceStatus 
					+ ".\n CIN content info: " + cin.getContentInfo() + " Content: " + cin.getContent());
			//TODO: FURTHER CIN PROCESSING HERE!
			System.out.println("Sub: "+ subscriptionReference + " retrieved Content Instance with status " + resourceStatus 
					+ ".\n CIN content info: " + cin.getContentInfo() + " Content: " + cin.getContent());
			
			
		} else {
			LOGGER.info("Sub: \"+ subscriptionReference + \" retrieved Resource with status " + resourceStatus 
					+ ".\n TYPE: " + resource.getClass().getName() + " " + resource.toString());
		}
	}	
}
