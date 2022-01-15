package com.fntech.subscriptionservice.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

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
public class SubscriptionDTO {

	@JsonProperty(access = Access.READ_ONLY)
	private long id;
	@JsonProperty(access = Access.READ_ONLY)
	private boolean paid;
	@JsonProperty(access = Access.READ_ONLY)
	private String userNumber;
	@JsonProperty(access = Access.READ_ONLY)
	private Date renewalDate;
	@NotNull(message = "planId is mandatory")
	private long planId;
}
