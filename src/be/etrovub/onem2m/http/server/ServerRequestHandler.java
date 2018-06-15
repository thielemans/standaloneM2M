package be.etrovub.onem2m.http.server;

import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;

public interface ServerRequestHandler {	
	public ResponsePrimitive doRequest(RequestPrimitive request);
}
