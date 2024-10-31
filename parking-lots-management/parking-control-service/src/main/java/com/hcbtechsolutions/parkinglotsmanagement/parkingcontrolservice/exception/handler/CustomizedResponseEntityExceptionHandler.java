package com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.exception.handler;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.dto.ExceptionDto;
import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.enums.VehicleTypeEnum;
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

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		String errorMessage;

		if (ex.getMessage().contains("not one of the values accepted for Enum")) {
			String validValues = Stream.of(VehicleTypeEnum.values())
					.map(Enum::name)
					.collect(Collectors.joining(", "));

			errorMessage = "Invalid value for Vehicle Type Enum. Accepted values are: " + validValues;
		} else {
			errorMessage = "Malformed JSON request or invalid data.";
		}

		ExceptionDto exception = new ExceptionDto(
				new Date(),
				errorMessage,
				request.getDescription(false));

		return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
	}
}
