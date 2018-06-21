package com.api.demo;

import java.util.Map;
@SuppressWarnings("all")
public class JsonToMap {
	public static Map<Object, Object> jsonToMap(Object jsonObj) {
		net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(jsonObj);
		Map<Object, Object> map = (Map)jsonObject;
		return map;
	}
}
