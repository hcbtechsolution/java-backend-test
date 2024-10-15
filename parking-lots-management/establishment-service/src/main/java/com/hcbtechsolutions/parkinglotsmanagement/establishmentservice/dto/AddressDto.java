package com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.dto;

import java.util.UUID;

import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.enums.StateEnum;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.model.Address;

public record AddressDto(
    UUID id,
    String name,
    String number,
    String complement,
    String district,
    String city,
    StateEnum state,
    String cep
) {

    public AddressDto(
        String name,
        String number,
        String complement,
        String district,
        String city,
        StateEnum state,
        String cep) {
        this(null, name, number, complement, district, city, state, cep);
    }

    public Address toModel() {
        return Address.builder()
            .id(this.id)
            .name(this.name)
            .number(this.number)
            .complement(this.complement)
            .district(this.district)
            .city(this.city)
            .state(this.state)
            .cep(this.cep)
            .build();
    }

    public static AddressDto fromModel(Address address) {
        return new AddressDto(
            address.getId(), 
            address.getName(), 
            address.getNumber(), 
            address.getComplement(), 
            address.getDistrict(), 
            address.getCity(), 
            address.getState(), 
            address.getCep());
    }
}
