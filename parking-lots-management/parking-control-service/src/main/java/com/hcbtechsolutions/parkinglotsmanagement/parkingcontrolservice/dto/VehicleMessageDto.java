package com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.dto;

import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.enums.VehicleTypeEnum;
import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.model.Vehicle;

public record VehicleMessageDto(
    String id,
    String licensePlate,
    VehicleTypeEnum type
) {

    public Vehicle toModel() {
        return Vehicle.builder().id(id).licensePlate(licensePlate).type(type).build();
    }
}
