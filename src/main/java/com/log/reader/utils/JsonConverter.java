package com.log.reader.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {

	private static ObjectMapper mapper = new ObjectMapper();

	public static <T> T toObject(String jsonString, Class<T> clazz) throws Exception {
		return mapper.readValue(jsonString, clazz);
	}

}
