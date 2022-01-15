package com.fntech.subscriptionservice.controllers;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fntech.subscriptionservice.dto.CardInfoDTO;
import com.fntech.subscriptionservice.exception.DuplicateDataException;
import com.fntech.subscriptionservice.exception.LimitExceededException;
import com.fntech.subscriptionservice.exception.NotFoundException;
import com.fntech.subscriptionservice.services.CardInfoService;

@RestController
@RequestMapping("/api/cards")
public class CardInfoController {

	private final CardInfoService cardInfoService;

	public CardInfoController(CardInfoService cardInfoService) {
		this.cardInfoService = cardInfoService;
	}

	@GetMapping
	@ResponseBody
	public ResponseEntity<List<CardInfoDTO>> getAllUserCards(Principal principal) {
		String userNumber = principal.getName();
		return new ResponseEntity<>(cardInfoService.getAllCardsByUserNumber(userNumber), HttpStatus.OK);
	}

	@PostMapping
	@ResponseBody
	public ResponseEntity<List<CardInfoDTO>> addNewCardInfo(Principal principal,@Valid @RequestBody CardInfoDTO cardInfoDTO)
			throws LimitExceededException, DuplicateDataException {
		String userNumber = principal.getName();
		return new ResponseEntity<>(cardInfoService.save(cardInfoDTO, userNumber), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	@ResponseBody
	public ResponseEntity<List<CardInfoDTO>> updateCardInfo(Principal principal,@Valid @RequestBody CardInfoDTO cardInfoDTO,
			@PathVariable long id) throws NotFoundException, DuplicateDataException {
		String userNumber = principal.getName();
		return new ResponseEntity<>(cardInfoService.update(cardInfoDTO, userNumber, id), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseEntity<List<CardInfoDTO>> deleteCardInfo(Principal principal, @PathVariable long id) {
		String userNumber = principal.getName();
		return new ResponseEntity<>(cardInfoService.delete(userNumber, id), HttpStatus.OK);
	}

}
