package com.fntech.subscriptionservice.enums;

public enum SubscriptionPlanDurationEnum {

	MONTH(1), YEAR(12);

	private final int duration;

	SubscriptionPlanDurationEnum(int duration) {
		this.duration = duration;
	}

	public int getDuration() {
		return duration;
	}

}
