package com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.exception.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.dto.ExceptionDto;
import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.exception.ResourceAlreadyExistsException;
import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.exception.ResourceNotFoundException;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionDto> handleAllExceptions(Exception ex, WebRequest request) {
		ExceptionDto exception = new ExceptionDto(
				new Date(),
				ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public final ResponseEntity<ExceptionDto> handleResourceAlreadyExistsException(Exception ex, WebRequest request) {
		ExceptionDto exception = new ExceptionDto(
				new Date(),
				ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(exception, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<ExceptionDto> handleResourceNotFoundException(Exception ex, WebRequest request) {
		ExceptionDto exception = new ExceptionDto(
				new Date(),
				ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
	}
}
