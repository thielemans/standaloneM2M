package be.etrovub.onem2m.util;

import java.math.BigInteger;

import org.eclipse.om2m.commons.resource.Resource;

/**
 * Container class for bundling Response status code and unmarshalled Resource. Used in combination with RequestSender.
 * Do note that in the case of an unmarshalling error or unsuccesful response, the resource field may be null.
 * @author Steffen Thielemans
 *
 */
public class Response {

	private BigInteger status;
	private Resource resource;
	
	public Response(BigInteger status, Resource resource) {
		this.status = status;
		this.resource = resource;
	}
	
	public BigInteger getStatus() {
		return status;
	}
	
	public Resource getResource() {
		return resource;
	}
}
