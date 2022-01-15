package com.fntech.subscriptionservice.dto;

import java.util.Date;

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
public class InvoiceDTO {
	
	@JsonProperty(access = Access.READ_ONLY)
	private long price;
	@JsonProperty(access = Access.READ_ONLY)
	private Date startDate;
	@JsonProperty(access = Access.READ_ONLY)
	private Date endDate;
	@JsonProperty(access = Access.READ_ONLY)
	private String userNumber;
	@JsonProperty(access = Access.READ_ONLY)
	private long subscriptionId;

}
