package com.fntech.subscriptionservice.controllers;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fntech.subscriptionservice.dto.CardInfoDTO;
import com.fntech.subscriptionservice.dto.InvoiceDTO;
import com.fntech.subscriptionservice.exception.NotFoundException;
import com.fntech.subscriptionservice.services.InvoiceService;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

	private final InvoiceService invoiceService;

	public InvoiceController(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	@GetMapping("/{subscriptionId}")
	@ResponseBody
	public ResponseEntity<InvoiceDTO> calculateInvoicePrice(Principal principal, @PathVariable long subscriptionId)
			throws NotFoundException {
		String userNumber = principal.getName();
		return new ResponseEntity<>(invoiceService.calculateInvoicePrice(userNumber, subscriptionId), HttpStatus.OK);
	}

	@PostMapping("/{subscriptionId}")
	@ResponseBody
	public ResponseEntity<InvoiceDTO> payInvoicePrice(Principal principal,@Valid @RequestBody CardInfoDTO cardInfoDTO,
			@PathVariable long subscriptionId) throws NotFoundException {
		String userNumber = principal.getName();
		return new ResponseEntity<>(invoiceService.payInvoicePrice(userNumber, subscriptionId, cardInfoDTO),
				HttpStatus.OK);
	}
}
