package com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.dto;

import java.io.Serializable;
import java.util.UUID;

import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.model.Establishment;

public record EstablishmentMessageDto(
    UUID uuid,
    String cnpj,
    Integer totalSlotsCar,
    Integer totalSlotsMotorcycle
) implements Serializable {

    public static EstablishmentMessageDto fromModel(Establishment establishment) {
        return new EstablishmentMessageDto(
            establishment.getId(),
            establishment.getCnpj(),
            establishment.getNumberSpaceCar(),
            establishment.getNumberSpaceMotorcycle()
        );
    }
}
