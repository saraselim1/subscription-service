package com.fntech.subscriptionservice.enums;

public enum PaymentMethodsEnum {

	MASTER_CARD("1", "Mastercard");

	private final String id;
	private final String name;

	PaymentMethodsEnum(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
