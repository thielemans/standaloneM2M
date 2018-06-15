package be.etrovub.onem2m.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.Resource;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;

import be.etrovub.onem2m.http.client.HttpClient;

public class RequestSender {
	
	private static Log LOGGER = LogFactory.getLog(RequestSender.class);

	private HttpClient httpClient;
	
	public RequestSender(HttpClient httpClient) {
		this.httpClient = httpClient;
	}
	
	public Response createOrGet(RequestPrimitive request) {
		ResponsePrimitive responsePrimitive = httpClient.sendRequest(request);
		LOGGER.info("Response status code on " + responsePrimitive.getFrom() + ": " + responsePrimitive.getResponseStatusCode());		
		if(responsePrimitive.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			try {
				Resource response = Marshaller.unmarshalResponse(responsePrimitive);
				return new Response(responsePrimitive.getResponseStatusCode(), response);
			} catch (MarshallingException e) {
				return new Response(ResponseStatusCode.INTERNAL_SERVER_ERROR, null);
			}
		} else if(responsePrimitive.getResponseStatusCode().equals(ResponseStatusCode.CONFLICT)){
			LOGGER.info(responsePrimitive.getFrom() + " already exists. Performing GET request.");
			ResponsePrimitive getResponsePrimitive = httpClient.sendRequest(RqpUtil.retrieveRqp(request.getFrom(), request.getTo()));
			if(getResponsePrimitive.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
				try {
					Resource response = Marshaller.unmarshalResponse(getResponsePrimitive);
					return new Response(getResponsePrimitive.getResponseStatusCode(), response);
				} catch (MarshallingException e) {
					return new Response(ResponseStatusCode.INTERNAL_SERVER_ERROR, null);
				}
			}
		}
		return new Response(ResponseStatusCode.INTERNAL_SERVER_ERROR, null);
	}
	
	//Currently not working as intended. Differences between Create and update:
	//Create: parent path and resource name set
	//Update: full path (parent+resource name) and resource name NOT set
	//Can be solved by combining RqpUtil and RequestSender
	@Deprecated
	public Response createOrUpdate(RequestPrimitive request) {
		ResponsePrimitive responsePrimitive = httpClient.sendRequest(request);
		LOGGER.info("Response status code on " + responsePrimitive.getFrom() + ": " + responsePrimitive.getResponseStatusCode());		
		if(responsePrimitive.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			try {
				Resource response = Marshaller.unmarshalResponse(responsePrimitive);
				return new Response(responsePrimitive.getResponseStatusCode(), response);
			} catch (MarshallingException e) {
				return new Response(ResponseStatusCode.INTERNAL_SERVER_ERROR, null);
			}
		} else if(responsePrimitive.getResponseStatusCode().equals(ResponseStatusCode.CONFLICT)){
			LOGGER.info(responsePrimitive.getFrom() + " already exists. Modifying into update request.");
			request.setOperation(Operation.UPDATE);
			ResponsePrimitive updateResponsePrimitive = httpClient.sendRequest(request);
			if(updateResponsePrimitive.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
				try {
					Resource response = Marshaller.unmarshalResponse(updateResponsePrimitive);
					return new Response(updateResponsePrimitive.getResponseStatusCode(), response);
				} catch (MarshallingException e) {
					return new Response(ResponseStatusCode.INTERNAL_SERVER_ERROR, null);
				}
			}
		}
		return new Response(ResponseStatusCode.INTERNAL_SERVER_ERROR, null);
	}	
	

	
}
