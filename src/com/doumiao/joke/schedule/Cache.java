package com.doumiao.joke.schedule;

import java.util.HashMap;

public class Cache {
	private static HashMap<Key, Object> data = new HashMap<Key, Object>();

	public enum Key {
		HOT_TEXT,HOT_PIC,AD,LOTTERY_LOG,UP_TEXT;
	}
	public static void set(Key key, Object value) {
		data.put(key, value);
	}

	public static Object get(Key key) {
		return data.get(key);
	}
}
