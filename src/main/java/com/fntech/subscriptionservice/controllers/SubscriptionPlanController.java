package com.fntech.subscriptionservice.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fntech.subscriptionservice.dto.SubscriptionPlanDTO;
import com.fntech.subscriptionservice.services.SubscriptionPlanService;

@RestController
@RequestMapping("/api/subscription-plan")
public class SubscriptionPlanController {

	private final SubscriptionPlanService subscribtionPlanService;

	public SubscriptionPlanController(SubscriptionPlanService subscribtionPlanService) {
		this.subscribtionPlanService = subscribtionPlanService;
	}

	@GetMapping
	@ResponseBody
	public ResponseEntity<List<SubscriptionPlanDTO>> getAllSubscriptionPlan() {

		return new ResponseEntity<>(subscribtionPlanService.getAllSubscriptionPlan(), HttpStatus.OK);

	}
}
