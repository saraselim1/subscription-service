package com.fntech.subscriptionservice.services.framework;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.fntech.subscriptionservice.dto.SubscriptionPlanDTO;
import com.fntech.subscriptionservice.repositories.SubscriptionPlanRepository;
import com.fntech.subscriptionservice.services.SubscriptionPlanService;

@Service
public class SubscriptionPlanImpl implements SubscriptionPlanService {

	private final SubscriptionPlanRepository subscribtionPlanRepository;
	private final ModelMapper modelMapper;

	public SubscriptionPlanImpl(SubscriptionPlanRepository subscribtionPlanRepository, ModelMapper modelMapper) {
		this.subscribtionPlanRepository = subscribtionPlanRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public List<SubscriptionPlanDTO> getAllSubscriptionPlan() {
		return subscribtionPlanRepository.findAll().stream().map(plan -> {
			return modelMapper.map(plan, SubscriptionPlanDTO.class);
		}).collect(Collectors.toList());
	}

}
