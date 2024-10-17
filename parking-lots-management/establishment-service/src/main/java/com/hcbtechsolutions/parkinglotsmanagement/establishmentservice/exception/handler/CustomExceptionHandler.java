package com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.exception.handler;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.dto.ExceptionDto;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.enums.StateEnum;
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<ExceptionDto> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex, WebRequest request) {
        String errorMessage;
        
        // Verifica se a mensagem de erro está relacionada ao enum
        if (ex.getMessage().contains("not one of the values accepted for Enum")) {
            String validValues = Stream.of(StateEnum.values())
                    .map(Enum::name)
                    .collect(Collectors.joining(", "));
            
            errorMessage = "Invalid value for State Enum. Accepted values are: " + validValues;
        } else {
            errorMessage = "Malformed JSON request or invalid data.";
        }

        // Cria o objeto de resposta com a mensagem adequada
        ExceptionDto exception = new ExceptionDto(
                new Date(),
                errorMessage,
                request.getDescription(false)
        );

        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }
}
