package com.hcbtechsolutions.parkinglotsmanagement.vehicleservice.dto;

import java.io.Serializable;

import com.hcbtechsolutions.parkinglotsmanagement.vehicleservice.enums.VehicleTypeEnum;
import com.hcbtechsolutions.parkinglotsmanagement.vehicleservice.model.Vehicle;

public record VehicleMessageDto(
        String id,
        String licensePlate,
        VehicleTypeEnum type) implements Serializable {

    public static VehicleMessageDto fromModel(Vehicle vehicle) {
        return new VehicleMessageDto(vehicle.getId(), vehicle.getLicensePlate(), vehicle.getType());
    }
}
