package com.fntech.subscriptionservice.dto;

import com.fntech.subscriptionservice.enums.SubscriptionPlanDurationEnum;
import com.fntech.subscriptionservice.enums.SubscriptionPlanTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionPlanDTO {

	private long id;
	private int price;
	private SubscriptionPlanDurationEnum duration;
	private SubscriptionPlanTypeEnum type;

}
