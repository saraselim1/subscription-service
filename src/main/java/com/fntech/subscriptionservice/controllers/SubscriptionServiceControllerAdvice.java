package com.fntech.subscriptionservice.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.hibernate.JDBCException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fntech.subscriptionservice.exception.DuplicateDataException;
import com.fntech.subscriptionservice.exception.InvalidPlanException;
import com.fntech.subscriptionservice.exception.LimitExceededException;
import com.fntech.subscriptionservice.exception.NotFoundException;
import com.fntech.subscriptionservice.exception.UnpaidServiceException;

@ControllerAdvice
public class SubscriptionServiceControllerAdvice {

	@ResponseBody
	@ExceptionHandler({ LimitExceededException.class, DuplicateDataException.class, NotFoundException.class,
			UnpaidServiceException.class, HttpRequestMethodNotSupportedException.class, InvalidPlanException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	ResponseEntity<String> exceptionHandler(Exception ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ MethodArgumentNotValidException.class, HttpMessageNotReadableException.class })
	public ResponseEntity<String> handleValidationExceptions(Exception ex) {
		return new ResponseEntity<>("BAD_REQUEST", HttpStatus.BAD_REQUEST);
	}

//	@ResponseStatus(value = HttpStatus.CONFLICT)
//	@ExceptionHandler({ ValidationException.class, DataIntegrityViolationException.class,
//			ConstraintViolationException.class, JDBCException.class })
//	public ResponseEntity<String> conflict() {
//		return new ResponseEntity<>("General Error", HttpStatus.CONFLICT);
//	}
}
