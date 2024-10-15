package com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.dto;

import java.util.UUID;

import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.model.Establishment;

public record EstablishmentDto(
    UUID id,
    String cnpj,
    String name,
    AddressDto address,
    PhoneDto phone,
    Integer numberSpaceMotocycle,
    Integer numberSpaceCar
) {
    public Establishment toModel() {
        return Establishment.builder()
            .id(this.id)
            .cnpj(this.cnpj)
            .name(this.name)
            .address(this.address.toModel())
            .phone(this.phone.toModel())
            .numberSpaceMotocycle(this.numberSpaceMotocycle)
            .numberSpaceCar(this.numberSpaceCar)
            .build();
    }

    public static EstablishmentDto fromModel(Establishment establishment) {
        return new EstablishmentDto(
            establishment.getId(), 
            establishment.getCnpj(), 
            establishment.getName(), 
            AddressDto.fromModel(establishment.getAddress()), 
            PhoneDto.fromModel(establishment.getPhone()), 
            establishment.getNumberSpaceMotocycle(), 
            establishment.getNumberSpaceCar());
    }
}
