package com.hcbtechsolutions.parkinglotsmanagement.vehicleservice.dto;

import com.hcbtechsolutions.parkinglotsmanagement.vehicleservice.enums.VehicleTypeEnum;
import com.hcbtechsolutions.parkinglotsmanagement.vehicleservice.model.Vehicle;

public record VehicleDto(
    String id,
    String brand,
    String model,
    String color,
    String licensePlate,
    VehicleTypeEnum type
) {

    public VehicleDto(String brand, String model, String color, String licensePlate, VehicleTypeEnum type) {
        this(null, brand, model, color, licensePlate.toUpperCase(), type);
    }

    public Vehicle toModel() {
        return Vehicle.builder()
            .id(id)
            .brand(brand)
            .model(model)
            .color(color)
            .licensePlate(licensePlate.toUpperCase())
            .type(type)
            .build();
    }

    public static VehicleDto fromModel(Vehicle vehicle) {
        return new VehicleDto(
            vehicle.getId(),
            vehicle.getBrand(),
            vehicle.getModel(),
            vehicle.getColor(),
            vehicle.getLicensePlate().toUpperCase(),
            vehicle.getType()
        );
    }
}
