package com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.enums.VehicleTypeEnum;
import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.model.ParkingLog;

public record ParkingLogDto(
        Long id,
        String establishmentId,
        String licensePlate,
        VehicleTypeEnum type,
        LocalDateTime checkIn,
        LocalDateTime checkOut) implements Serializable {

    public static ParkingLogDto fromModel(ParkingLog parkingLog) {
        return new ParkingLogDto(
                parkingLog.getId(),
                parkingLog.getEstablishmentUuid(),
                parkingLog.getLicensePlate(),
                parkingLog.getType(),
                parkingLog.getCheckIn(),
                parkingLog.getCheckOut());
    }
}
