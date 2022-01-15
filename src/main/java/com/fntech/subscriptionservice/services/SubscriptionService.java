package com.fntech.subscriptionservice.services;

import com.fntech.subscriptionservice.dto.SubscriptionDTO;
import com.fntech.subscriptionservice.exception.DuplicateDataException;
import com.fntech.subscriptionservice.exception.InvalidPlanException;
import com.fntech.subscriptionservice.exception.NotFoundException;
import com.fntech.subscriptionservice.exception.UnpaidServiceException;

public interface SubscriptionService {

	SubscriptionDTO upgradeSubscriptionService(SubscriptionDTO subscriptionDTO, String userNumber, long id)
			throws UnpaidServiceException, DuplicateDataException, NotFoundException,InvalidPlanException;

	void delete(String userNumber, long id) throws UnpaidServiceException;

	SubscriptionDTO addSubscriptionService(SubscriptionDTO subscriptionDTO, String userNumber)
			throws DuplicateDataException, NotFoundException;

}
