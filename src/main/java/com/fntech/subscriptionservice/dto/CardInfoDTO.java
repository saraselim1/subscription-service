package com.fntech.subscriptionservice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

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
public class CardInfoDTO {

	@JsonProperty(access = Access.READ_ONLY)
	private long id;
	@NotEmpty(message = "cardNumber is mandatory")
	private String cardNumber;
	@NotBlank(message = "expireDate is mandatory")
	@Length(min = 4, max = 5)
	private String expireDate;

}
