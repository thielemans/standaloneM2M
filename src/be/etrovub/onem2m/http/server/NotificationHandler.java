package be.etrovub.onem2m.http.server;

import org.eclipse.om2m.commons.resource.Notification.NotificationEvent;

public interface NotificationHandler {	
	public void parseNotification(String subscriptionReference, NotificationEvent event);
}
