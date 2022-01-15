package com.fntech.subscriptionservice.services.framework;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.fntech.subscriptionservice.domain.Subscription;
import com.fntech.subscriptionservice.domain.SubscriptionPlan;
import com.fntech.subscriptionservice.dto.SubscriptionDTO;
import com.fntech.subscriptionservice.enums.SubscriptionPlanDurationEnum;
import com.fntech.subscriptionservice.exception.DuplicateDataException;
import com.fntech.subscriptionservice.exception.InvalidPlanException;
import com.fntech.subscriptionservice.exception.NotFoundException;
import com.fntech.subscriptionservice.exception.UnpaidServiceException;
import com.fntech.subscriptionservice.repositories.SubscriptionPlanRepository;
import com.fntech.subscriptionservice.repositories.SubscriptionRepository;
import com.fntech.subscriptionservice.services.SubscriptionService;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	private final SubscriptionRepository subscriptionRepository;
	private final SubscriptionPlanRepository subscriptionPlanRepository;
	private final ModelMapper modelMapper;

	public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository,
			SubscriptionPlanRepository subscriptionPlanRepository, ModelMapper modelMapper) {
		this.subscriptionRepository = subscriptionRepository;
		this.subscriptionPlanRepository = subscriptionPlanRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public SubscriptionDTO addSubscriptionService(SubscriptionDTO subscriptionDTO, String userNumber)
			throws DuplicateDataException, NotFoundException {

		if (subscriptionRepository.findByUserNumber(userNumber).isPresent()) {
			throw new DuplicateDataException("User Already Subscrip.");
		} else {

			Subscription subscription = convertToEntity(subscriptionDTO);
			subscription.setUserNumber(userNumber);
			subscription.setPaid(false);

			Date renewalDate = null;

			SubscriptionPlanDurationEnum duration = subscriptionPlanRepository.findById(subscriptionDTO.getPlanId())
					.map(SubscriptionPlan::getDuration).orElseThrow(() -> new NotFoundException("Plan Not found."));

			switch (duration) {
			case MONTH:
				renewalDate = Date
						.from(LocalDate.now().plusMonths(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
				break;
			case YEAR:
				renewalDate = Date
						.from(LocalDate.now().plusYears(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
				break;
			default:
				break;
			}

			subscription.setRenewalDate(renewalDate);

			subscription = subscriptionRepository.save(subscription);
			return convertToDTO(subscription);
		}
	}

	@Override
	public SubscriptionDTO upgradeSubscriptionService(SubscriptionDTO subscriptionDTO, String userNumber, long id)
			throws UnpaidServiceException, DuplicateDataException, NotFoundException, InvalidPlanException {
		Optional<Subscription> oldSubscriptionOptional = subscriptionRepository.findByIdAndUserNumber(id, userNumber);
		if (oldSubscriptionOptional.isPresent()) {
			Subscription oldSubscription = oldSubscriptionOptional.get();
			if (oldSubscription.isPaid()) {
				Optional<SubscriptionPlan> newSubscriptionPlanOptional = subscriptionPlanRepository
						.findById(subscriptionDTO.getPlanId());
				if (newSubscriptionPlanOptional.isPresent()) {
					SubscriptionPlan oldSubscriptionPlan = oldSubscription.getPlan();
					SubscriptionPlan newSubscriptionPlan = newSubscriptionPlanOptional.get();

					if (newSubscriptionPlan.getDuration().getDuration() > oldSubscriptionPlan.getDuration()
							.getDuration()
							|| newSubscriptionPlan.getType().getLevel() > oldSubscriptionPlan.getType().getLevel()) {
						subscriptionRepository.delete(oldSubscription);
						return this.addSubscriptionService(subscriptionDTO, userNumber);
					} else {
						throw new InvalidPlanException("Upgrade to higher not lower plan.");
					}
				} else {
					throw new NotFoundException("Subscription Plan not found.");
				}
			} else {
				throw new UnpaidServiceException("Please finish the payment process first");
			}
		}
		return null;
	}

	@Override
	public void delete(String userNumber, long id) throws UnpaidServiceException {

		Optional<Subscription> subscriptionOptional = subscriptionRepository.findByIdAndUserNumber(id, userNumber);
		if (subscriptionOptional.isPresent()) {
			Subscription subscription = subscriptionOptional.get();
			if (subscription.isPaid()) {
				subscriptionRepository.delete(subscription);
			} else {
				throw new UnpaidServiceException("Please finish the payment process first");
			}
		}
	}

	private Subscription convertToEntity(SubscriptionDTO subscriptionDTO) {
		return modelMapper.map(subscriptionDTO, Subscription.class);
	}

	private SubscriptionDTO convertToDTO(Subscription subscription) {
		return modelMapper.map(subscription, SubscriptionDTO.class);
	}
}
