package orderservice.server.core.basic;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

	
	public static <T> T parseObject(String json, Class<T> cls,Include include) {

		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(include);
		try {
			return mapper.readValue(json, cls);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static <T> T parseObject(String json, Class<T> cls) {

		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(json, cls);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static String stringify(Object obj,Include include) {

		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(include);
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			
			return null;
		}
	}

	public static String stringify(Object obj) {

		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

}
