package be.etrovub.onem2m.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.resource.Resource;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.datamapping.jaxb.Mapper;

public class Marshaller {
	private static Map<String,Mapper> dataMappers = new HashMap<String, Mapper>();
	static {
		//Only use XML for now
		dataMappers.put(MimeMediaType.XML, new Mapper(MimeMediaType.XML));
		dataMappers.put(MimeMediaType.JSON, new Mapper(MimeMediaType.JSON));
	}
	
	public static Mapper getMapper(String mapper) {
		return dataMappers.get(mapper);
	}
	
	public static Set<String> getMappings(){
		return dataMappers.keySet();
	}
		
	public static Resource unmarshalResponse(ResponsePrimitive responsePrimitive) throws MarshallingException {
		Mapper myMapper;
		
		// getContentType might also contain other things like charset appended to it. 
		// We check if getContentType CONTAINS a supported mapping (e.g. application/xml) and use the first matching mapper.
		Optional<String> possibleMapper = getMappings().stream().filter(responsePrimitive.getContentType()::contains).findFirst();
		if(possibleMapper.isPresent()) {
			myMapper = getMapper(possibleMapper.get());
		} else {
			throw new MarshallingException("No supported mapper found for " + responsePrimitive.getContentType());
		}
		
		Object o = myMapper.stringToObj((String) responsePrimitive.getContent());
		if(!(o instanceof Resource)) {
			throw new MarshallingException("Unmarshalled content is not a supported oneM2M resource: " + o.toString());
		}
		return (Resource)o;
	}
}
