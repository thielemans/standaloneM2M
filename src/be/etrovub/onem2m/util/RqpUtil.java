package be.etrovub.onem2m.util;

import java.math.BigInteger;

import org.eclipse.om2m.commons.constants.FilterUsage;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.resource.FilterCriteria;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.Resource;

public class RqpUtil {
	public static String defaultMimeMediaType = MimeMediaType.XML;
	
	/*
	 * EMPTY REQUEST
	 */
	public static RequestPrimitive basicRqp(String from, String to) {
		RequestPrimitive rqp = new RequestPrimitive();
		rqp.setFrom(from);
		rqp.setTo(to);
		return rqp;
	}

/*
 * CREATE REQUESTS	
 */
	public static RequestPrimitive createRqpWithContent(String from, String to, int resourceType, String content, String contentType) {
		RequestPrimitive rqp = basicRqp(from, to);
		rqp.setOperation(Operation.CREATE);
		rqp.setResourceType(resourceType);
		rqp.setContent(content);
		rqp.setRequestContentType(contentType);
		return rqp;		
	}
	
	public static RequestPrimitive createRqpWithContent(String from, String to, int resourceType, String content) {
		return createRqpWithContent(from, to, resourceType, content, defaultMimeMediaType);
	}
	
	public static RequestPrimitive createRqpWithContentAndResult(String from, String to, int resourceType, 
			String content, String contentType, BigInteger resultContent, String returnContentType) {
		RequestPrimitive rqp = createRqpWithContent(from, to, resourceType, content, contentType);
		rqp.setResultContent(resultContent);
		rqp.setReturnContentType(returnContentType);
		return rqp;		
	}
	
	public static RequestPrimitive createRqpWithContentAndResult(String from, String to, int resourceType, String content, BigInteger resultContent) {
		return createRqpWithContentAndResult(from, to, resourceType, content, defaultMimeMediaType, resultContent, defaultMimeMediaType);		
	}
	
	public static RequestPrimitive createRqpWithContent(String from, String to, int resourceType, Resource resource, String contentType) {
		String content = Marshaller.getMapper(contentType).objToString(resource);
		return createRqpWithContent(from, to, resourceType, content, contentType);
	}
	
	public static RequestPrimitive createRqpWithContent(String from, String to, int resourceType, Resource resource) {
		String content = Marshaller.getMapper(defaultMimeMediaType).objToString(resource);
		return createRqpWithContent(from, to, resourceType, content, defaultMimeMediaType);
	}
	
	public static RequestPrimitive createRqpWithContentAndResult(String from, String to, int resourceType, 
			Resource resource, String contentType, BigInteger resultContent, String returnContentType) {
		String content = Marshaller.getMapper(contentType).objToString(resource);
		return createRqpWithContentAndResult(from, to, resourceType, content, contentType, resultContent, returnContentType);		
	}
	
	public static RequestPrimitive createRqpWithContentAndResult(String from, String to, int resourceType, Resource resource, BigInteger resultContent) {
		String content = Marshaller.getMapper(defaultMimeMediaType).objToString(resource);
		return createRqpWithContentAndResult(from, to, resourceType, content, defaultMimeMediaType, resultContent, defaultMimeMediaType);		
	}
	
	
	/*
	 * RETRIEVE REQUESTS
	 */
	public static RequestPrimitive retrieveRqp(String from, String to) {
		RequestPrimitive rqp = basicRqp(from, to);
		rqp.setOperation(Operation.RETRIEVE);
		return rqp;
	}
	
	public static RequestPrimitive retrieveRqpWithResult(String from, String to, BigInteger resultContent, String returnContentType) {
		RequestPrimitive rqp = retrieveRqp(from, to);
		rqp.setResultContent(resultContent);
		rqp.setReturnContentType(returnContentType);
		return rqp;
	}
	
	public static RequestPrimitive retrieveRqpWithResult(String from, String to, BigInteger resultContent) {
		return retrieveRqpWithResult(from, to, resultContent, defaultMimeMediaType);
	}
	
	/*
	 * UPDATE REQUESTS
	 */
	public static RequestPrimitive updateRqpWithContent(String from, String to, String content, String contentType) {
		RequestPrimitive rqp = basicRqp(from, to);
		rqp.setOperation(Operation.UPDATE);
		rqp.setContent(content);
		rqp.setRequestContentType(contentType);
		return rqp;		
	}
	
	public static RequestPrimitive updateRqpWithContent(String from, String to, String content) {
		return updateRqpWithContent(from, to, content, defaultMimeMediaType);
	}
	
	public static RequestPrimitive updateRqpWithContentAndResult(String from, String to, String content, 
			String contentType, BigInteger resultContent, String returnContentType) {
		RequestPrimitive rqp = updateRqpWithContent(from, to, content, contentType);
		rqp.setResultContent(resultContent);
		rqp.setReturnContentType(returnContentType);
		return rqp;
	}
	
	public static RequestPrimitive updateRqpWithContentAndResult(String from, String to, String content, BigInteger resultContent) {
		return updateRqpWithContentAndResult(from, to, content, defaultMimeMediaType, resultContent, defaultMimeMediaType);
	}
	
	public static RequestPrimitive updateRqpWithContent(String from, String to, Resource resource, String contentType) {
		String content = Marshaller.getMapper(contentType).objToString(resource);
		RequestPrimitive rqp = basicRqp(from, to);
		rqp.setOperation(Operation.UPDATE);
		rqp.setContent(content);
		rqp.setRequestContentType(contentType);
		return rqp;		
	}
	
	public static RequestPrimitive updateRqpWithContent(String from, String to, Resource resource) {
		String content = Marshaller.getMapper(defaultMimeMediaType).objToString(resource);
		return updateRqpWithContent(from, to, content, defaultMimeMediaType);
	}
	
	public static RequestPrimitive updateRqpWithContentAndResult(String from, String to, Resource resource, 
			String contentType, BigInteger resultContent, String returnContentType) {
		String content = Marshaller.getMapper(contentType).objToString(resource);
		RequestPrimitive rqp = updateRqpWithContent(from, to, content, contentType);
		rqp.setResultContent(resultContent);
		rqp.setReturnContentType(returnContentType);
		return rqp;
	}
	
	public static RequestPrimitive updateRqpWithContentAndResult(String from, String to, Resource resource, BigInteger resultContent) {
		String content = Marshaller.getMapper(defaultMimeMediaType).objToString(resource);
		return updateRqpWithContentAndResult(from, to, content, defaultMimeMediaType, resultContent, defaultMimeMediaType);
	}
	
	/*
	 * DELETE REQUESTS
	 */
	public static RequestPrimitive deleteRqp(String from, String to) {
		RequestPrimitive rqp = basicRqp(from, to);
		rqp.setOperation(Operation.DELETE);
		return rqp;
	}
	
	public static RequestPrimitive deleteRqpWithResult(String from, String to, BigInteger resultContent, String returnContentType) {
		RequestPrimitive rqp = deleteRqp(from, to);
		rqp.setResultContent(resultContent);
		rqp.setReturnContentType(returnContentType);
		return rqp;
	}
	
	public static RequestPrimitive deleteRqpWithResult(String from, String to, BigInteger resultContent) {
		return deleteRqpWithResult(from, to, resultContent, defaultMimeMediaType);
	}
	
	/*
	 * NOTIFY REQUESTS
	 */
	public static RequestPrimitive notifyRqpWithContent(String from, String to, String content, String contentType) {
		RequestPrimitive rqp = basicRqp(from, to);
		rqp.setContent(content);
		rqp.setRequestContentType(contentType);
		return null;
	}
	
	public static RequestPrimitive notifyRqpWithContent(String from, String to, String content) {
		return notifyRqpWithContent(from, to, content, defaultMimeMediaType);
	}
	
	/*
	 * DISCOVERY REQUESTS
	 */
	public static RequestPrimitive discoveryRqp(String from, String to, FilterCriteria fc) {
		RequestPrimitive rqp = basicRqp(from, to);
		rqp.setOperation(Operation.RETRIEVE);
		fc.setFilterUsage(FilterUsage.DISCOVERY_CRITERIA);
		rqp.setFilterCriteria(fc);
		return rqp;
	}
	
	public static RequestPrimitive discoveryRqpWithResult(String from, String to, FilterCriteria fc, BigInteger drt) {
		RequestPrimitive rqp = discoveryRqp(from, to, fc);
		rqp.setDiscoveryResultType(drt);
		return rqp;
	}

	public static String getDefaultMimeMediaType() {
		return defaultMimeMediaType;
	}

	public static void setDefaultMimeMediaType(String defaultMimeMediaType) {
		RqpUtil.defaultMimeMediaType = defaultMimeMediaType;
	}
}
