package com.fntech.subscriptionservice.services;

import com.fntech.subscriptionservice.dto.CardInfoDTO;
import com.fntech.subscriptionservice.dto.InvoiceDTO;
import com.fntech.subscriptionservice.exception.NotFoundException;

public interface InvoiceService {

	InvoiceDTO calculateInvoicePrice(String userNumber, long subscriptionId) throws NotFoundException;

	InvoiceDTO payInvoicePrice(String userNumber, long subscriptionId, CardInfoDTO cardInfoDTO) throws NotFoundException;

}
