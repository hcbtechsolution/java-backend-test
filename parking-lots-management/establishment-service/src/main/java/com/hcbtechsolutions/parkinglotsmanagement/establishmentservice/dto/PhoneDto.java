package com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.dto;

import java.io.Serializable;
import java.util.UUID;

import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.model.Phone;

public record PhoneDto(
    UUID id,
    String ddd,
    String number
) implements Serializable {
    
    public PhoneDto(String ddd, String number) {
        this(null, ddd, number);
    }

    public Phone toModel() {
        return Phone.builder().id(this.id).ddd(this.ddd).number(this.number).build();
    }

    public static PhoneDto fromModel(Phone phone) {
        return new PhoneDto(phone.getId(), phone.getDdd(), phone.getNumber());
    }
}
