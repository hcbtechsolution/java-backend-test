package com.hcbtechsolutions.parkinglotsmanagement.vehicleservice.exception.handler;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.hcbtechsolutions.parkinglotsmanagement.vehicleservice.dto.ExceptionDto;
import com.hcbtechsolutions.parkinglotsmanagement.vehicleservice.enums.VehicleTypeEnum;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<ExceptionDto> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex, WebRequest request) {
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
                request.getDescription(false)
        );

        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }
}
