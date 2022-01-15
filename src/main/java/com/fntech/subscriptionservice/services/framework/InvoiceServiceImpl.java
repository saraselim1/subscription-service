package com.fntech.subscriptionservice.services.framework;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fntech.subscriptionservice.domain.CardInfo;
import com.fntech.subscriptionservice.domain.Subscription;
import com.fntech.subscriptionservice.dto.CardInfoDTO;
import com.fntech.subscriptionservice.dto.InvoiceDTO;
import com.fntech.subscriptionservice.exception.NotFoundException;
import com.fntech.subscriptionservice.repositories.CardInfoRepository;
import com.fntech.subscriptionservice.repositories.SubscriptionRepository;
import com.fntech.subscriptionservice.services.InvoiceService;

@Service
public class InvoiceServiceImpl implements InvoiceService {

	private final SubscriptionRepository subscribtionRepository;
	private final CardInfoRepository cardInfoRepository;

	private static final int DAYS_OF_MONTH = 30;
	private static final int DAYS_OF_YEAR = 365;

	public InvoiceServiceImpl(SubscriptionRepository subscribtionRepository, CardInfoRepository cardInfoRepository) {
		this.subscribtionRepository = subscribtionRepository;
		this.cardInfoRepository = cardInfoRepository;
	}

	@Override
	public InvoiceDTO calculateInvoicePrice(String userNumber, long subscriptionId) throws NotFoundException {

		return subscribtionRepository.findByIdAndUserNumber(subscriptionId, userNumber).map(this::calculateInvoicePrice)
				.orElseThrow(() -> new NotFoundException("Subscription Not found."));

	}

	@Override
	public InvoiceDTO payInvoicePrice(String userNumber, long subscriptionId, CardInfoDTO cardInfoDTO)
			throws NotFoundException {
		InvoiceDTO invoiceDTO = subscribtionRepository.findByIdAndUserNumber(subscriptionId, userNumber)
				.map(this::calculateInvoicePrice).orElseThrow(() -> new NotFoundException("Subscription Not found."));

		Optional<CardInfo> cardOptional = cardInfoRepository.findByCardNumberAndUserNumber(cardInfoDTO.getCardNumber(),
				userNumber);
		if (cardOptional.isEmpty()) {
			throw new NotFoundException("Card Not found.");
		}
		paymentTask(invoiceDTO, cardInfoDTO);

		return invoiceDTO;
	}

	private void paymentTask(InvoiceDTO invoiceDTO, CardInfoDTO cardInfoDTO) throws NotFoundException {
		subscribtionRepository.findById(invoiceDTO.getSubscriptionId()).map(entity -> {
			entity.setPaid(true);
			return subscribtionRepository.save(entity);
		}).orElseThrow(() -> new NotFoundException("Card Not found."));
	}

	private InvoiceDTO calculateInvoicePrice(Subscription subscription) {

		long pricePerDay = 0;
		long numberOfSubscriptionDays = 0;
		long invoiceValue;
		Date startDate = null;
		LocalDate renewalData = subscription.getRenewalDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		switch (subscription.getPlan().getDuration()) {
		case MONTH:
			pricePerDay = subscription.getPlan().getPrice() / DAYS_OF_MONTH;
			numberOfSubscriptionDays = DAYS_OF_MONTH - ChronoUnit.DAYS.between(LocalDate.now(), renewalData);
			startDate = Date.from(renewalData.minusMonths(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			break;
		case YEAR:
			pricePerDay = subscription.getPlan().getPrice() / DAYS_OF_YEAR;
			numberOfSubscriptionDays = DAYS_OF_YEAR - ChronoUnit.DAYS.between(renewalData, LocalDate.now());
			startDate = Date.from(renewalData.minusYears(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			break;
		default:
			break;
		}

		invoiceValue = Math.abs(pricePerDay * numberOfSubscriptionDays);
		return InvoiceDTO.builder().price(invoiceValue).endDate(new Date()).startDate(startDate)
				.userNumber(subscription.getUserNumber()).subscriptionId(subscription.getId()).build();
	}

}
