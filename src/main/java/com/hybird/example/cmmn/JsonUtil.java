package com.hybird.example.cmmn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class JsonUtil {

	/**
	 *  json -> map<String, Object>
	 */
	public static Map<String, Object> jsonToMap(String json_str) throws JsonProcessingException {
		return new ObjectMapper().readValue(json_str, Map.class);
	}

	/**
	 * map<String, Object> -> json
	 */
	public static String mapToJson(Map<String, Object> map) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(map);
	}

	/**
	 * map<String, Object> -> json
	 */
	public static String mapToJsonInt(Map<String, Integer> map) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(map);
	}

	/**
	 * map<String, Object> -> json
	 * @return
	 */
	public static  Map<String, Object> objToMap(Object obj) throws JsonProcessingException {
		return new ObjectMapper().convertValue(obj, Map.class);
	}
}
