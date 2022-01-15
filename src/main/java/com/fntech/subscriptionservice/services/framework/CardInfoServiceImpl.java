package com.fntech.subscriptionservice.services.framework;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.fntech.subscriptionservice.domain.CardInfo;
import com.fntech.subscriptionservice.dto.CardInfoDTO;
import com.fntech.subscriptionservice.exception.DuplicateDataException;
import com.fntech.subscriptionservice.exception.LimitExceededException;
import com.fntech.subscriptionservice.exception.NotFoundException;
import com.fntech.subscriptionservice.repositories.CardInfoRepository;
import com.fntech.subscriptionservice.services.CardInfoService;

@Service
public class CardInfoServiceImpl implements CardInfoService {

	private final CardInfoRepository cardInfoRepository;
	private final ModelMapper modelMapper;

	public CardInfoServiceImpl(CardInfoRepository cardInfoRepository, ModelMapper modelMapper) {
		this.cardInfoRepository = cardInfoRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public List<CardInfoDTO> getAllCardsByUserNumber(String userNumber) {
		return cardInfoRepository.getAllCardsByUserNumber(userNumber).stream().map(this::convertToDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<CardInfoDTO> save(CardInfoDTO cardInfoDTO, String userNumber)
			throws LimitExceededException, DuplicateDataException {

		if (cardInfoRepository.findByCardNumber(cardInfoDTO.getCardNumber()).isPresent()) {
			throw new DuplicateDataException("Card Already used.");
		}

		if (cardInfoRepository.countByUserNumber(userNumber) < 3) {
			CardInfo newEntity = convertToEntity(cardInfoDTO);
			newEntity.setUserNumber(userNumber);
			cardInfoRepository.save(newEntity);
			return getAllCardsByUserNumber(userNumber);
		} else {
			throw new LimitExceededException("Number of Max. Cards has been exceed.");
		}
	}

	@Override
	public List<CardInfoDTO> update(CardInfoDTO cardInfoDTO, String userNumber, long id)
			throws NotFoundException, DuplicateDataException {
		if (cardInfoRepository.findByCardNumber(cardInfoDTO.getCardNumber()).isPresent()) {
			throw new DuplicateDataException("Card Already used.");
		}

		cardInfoRepository.findByIdAndUserNumber(id, userNumber).map(card -> {
			CardInfo cardInfo = convertToEntity(cardInfoDTO);
			cardInfo.setUserNumber(userNumber);
			cardInfo.setId(id);
			return cardInfoRepository.save(cardInfo);
		}).orElseThrow(() -> new NotFoundException("Card Not found."));

		return getAllCardsByUserNumber(userNumber);
	}

	@Override
	public List<CardInfoDTO> delete(String userNumber, long id) {
		cardInfoRepository.findByIdAndUserNumber(id, userNumber).ifPresent(card -> cardInfoRepository.delete(card));
		return getAllCardsByUserNumber(userNumber);
	}

	private CardInfoDTO convertToDto(CardInfo cardInfo) {
		return modelMapper.map(cardInfo, CardInfoDTO.class);
	}

	private CardInfo convertToEntity(CardInfoDTO cardInfoDTO) {
		return modelMapper.map(cardInfoDTO, CardInfo.class);
	}

}
