package com.doumiao.joke.schedule;

import java.util.HashMap;
import java.util.Map;

public class Config {
	private final static HashMap<String, String> config = new HashMap<String, String>();

	public static void set(String key, String value) {
		config.put(key, value);
	}

	public static String get(String key) {
		return config.get(key);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,String> getConfig(){
		return (Map<String,String>)config.clone();
	}
}
