package com.fntech.subscriptionservice.enums;

public enum SubscriptionPlanTypeEnum {
	BASIC(1), STANDARD(2), PREMIUM(3);

	private final int level;

	SubscriptionPlanTypeEnum(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}
}
