package com.fntech.subscriptionservice.controllers;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fntech.subscriptionservice.dto.SubscriptionDTO;
import com.fntech.subscriptionservice.exception.DuplicateDataException;
import com.fntech.subscriptionservice.exception.InvalidPlanException;
import com.fntech.subscriptionservice.exception.NotFoundException;
import com.fntech.subscriptionservice.exception.UnpaidServiceException;
import com.fntech.subscriptionservice.services.SubscriptionService;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {

	private final SubscriptionService subscribtionService;

	public SubscriptionController(SubscriptionService subscribtionService) {
		this.subscribtionService = subscribtionService;
	}

	@PostMapping
	@ResponseBody
	public ResponseEntity<SubscriptionDTO> subscriptionService(Principal principal,
			@Valid @RequestBody SubscriptionDTO subscriptionDTO) throws DuplicateDataException, NotFoundException {
		String userNumber = principal.getName();
		return new ResponseEntity<>(subscribtionService.addSubscriptionService(subscriptionDTO, userNumber),
				HttpStatus.OK);
	}

	@PutMapping("/{id}")
	@ResponseBody
	public ResponseEntity<SubscriptionDTO> upgradeSubscriptionService(Principal principal,
			@Valid @RequestBody SubscriptionDTO subscriptionDTO, @PathVariable long id)
			throws UnpaidServiceException, DuplicateDataException, NotFoundException, InvalidPlanException {
		String userNumber = principal.getName();
		return new ResponseEntity<>(subscribtionService.upgradeSubscriptionService(subscriptionDTO, userNumber, id),
				HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseEntity<Void> cancelSubscriptionService(Principal principal, @PathVariable long id)
			throws UnpaidServiceException {
		String userNumber = principal.getName();
		subscribtionService.delete(userNumber, id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
