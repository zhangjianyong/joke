package com.doumiao.joke.enums;

public enum OrderType {
	NEW, TODAY(1), WEEK(7), MONTH(30);
	private int value;

	OrderType() {
	};

	OrderType(int v) {
		this.value = v;
	}

	public int getValue() {
		return value;
	}
}