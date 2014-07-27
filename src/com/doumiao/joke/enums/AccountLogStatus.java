package com.doumiao.joke.enums;

public enum AccountLogStatus {
	UNCHECK("未审核", 0), VALID("有效", 1), INVALID("无效", 2);
	private int value;
	private String name;

	private AccountLogStatus(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public static AccountLogStatus valueOf(int value) {
		switch (value) {
		case 0:
			return UNCHECK;
		case 1:
			return VALID;
		case 2:
			return INVALID;
		default:
			return null;
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}