package com.fntech.subscriptionservice.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fntech.subscriptionservice.enums.PaymentMethodsEnum;

@RestController
@RequestMapping("/api/payment-metod")
public class PaymentMethodController {

	@GetMapping
	@ResponseBody
	public ResponseEntity<List<Map<String, String>>> getAllPaymentMethods() {

		List<Map<String, String>> methods = Stream.of(PaymentMethodsEnum.values()).parallel().map(method -> {
			Map<String, String> obj = new HashMap<>();
			obj.put("id", method.getId());
			obj.put("name", method.getName());
			return obj;
		}).collect(Collectors.toList());

		return new ResponseEntity<>(methods, HttpStatus.OK);

	}
}
