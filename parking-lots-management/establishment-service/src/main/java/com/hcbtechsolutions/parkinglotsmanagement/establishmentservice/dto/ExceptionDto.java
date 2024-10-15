package com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.dto;

import java.io.Serializable;
import java.util.Date;

public record ExceptionDto(
    Date timestamp, 
    String message, 
    String details) implements Serializable {
}
