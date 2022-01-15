package com.fntech.subscriptionservice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fntech.subscriptionservice.enums.SubscriptionPlanDurationEnum;
import com.fntech.subscriptionservice.enums.SubscriptionPlanTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "SUBSCRIPTION_PLAN")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionPlan {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "PRICE")
	private int price;

	@Column(name = "DURATION")
	@Enumerated(EnumType.STRING)
	private SubscriptionPlanDurationEnum duration;

	@Column(name = "SUBSCRIPTION_TYPE")
	@Enumerated(EnumType.STRING)
	private SubscriptionPlanTypeEnum type;

}
