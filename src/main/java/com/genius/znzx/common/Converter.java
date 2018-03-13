package com.genius.znzx.common;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class Converter {
	
	private static Gson gson = new Gson();
	/**
	 * 
	 * 将json格式转换成map对象
	 * 
	 * @param jsonStr
	 * 
	 * @return
	 * 
	 */
	public static Map<String, Object> jsonToMap(String jsonStr) {
		Map<String, Object> objMap = null;
		if (gson != null) {
			java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<Map<String, Object>>() {
			}.getType();
			objMap = gson.fromJson(jsonStr, type);
		}
		return objMap;
	}
}
