package com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.dto;

import java.io.Serializable;

import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.model.Establishment;

public record EstablishmentMessageDto(
    String uuid,
    String cnpj,
    Integer totalSlotsCar,
    Integer totalSlotsMotorcycle
) implements Serializable {

    public Establishment toModel() {
        return Establishment.builder()
            .uuid(uuid)
            .cnpj(cnpj)
            .totalSlotsCar(totalSlotsCar)
            .totalSlotsMotorcycle(totalSlotsMotorcycle)
            .build();
    }
}
