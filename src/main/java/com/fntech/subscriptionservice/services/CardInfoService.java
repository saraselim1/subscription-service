package com.fntech.subscriptionservice.services;

import java.util.List;

import com.fntech.subscriptionservice.dto.CardInfoDTO;
import com.fntech.subscriptionservice.exception.DuplicateDataException;
import com.fntech.subscriptionservice.exception.LimitExceededException;
import com.fntech.subscriptionservice.exception.NotFoundException;

public interface CardInfoService {

	List<CardInfoDTO> getAllCardsByUserNumber(String userNumber);

	List<CardInfoDTO> save(CardInfoDTO cardInfoDTO, String userNumber) throws LimitExceededException,DuplicateDataException;

	List<CardInfoDTO> update(CardInfoDTO cardInfoDTO, String userNumber, long id) throws NotFoundException,DuplicateDataException;

	List<CardInfoDTO> delete(String userNumber, long id) ;

}
