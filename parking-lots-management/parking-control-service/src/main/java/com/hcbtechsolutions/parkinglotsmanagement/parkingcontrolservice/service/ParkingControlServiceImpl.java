package com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.dto.ParkingLogDto;
import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.enums.VehicleTypeEnum;
import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.exception.ResourceAlreadyExistsException;
import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.exception.ResourceNotFoundException;
import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.model.Establishment;
import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.model.ParkingLog;
import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.model.Vehicle;
import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.repository.EstablishmentRepository;
import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.repository.ParkingLogRepository;
import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.repository.VehicleRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ParkingControlServiceImpl implements ParkingControlService {

    EstablishmentRepository establishmentRepository;
    VehicleRepository vehicleRepository;
    ParkingLogRepository repository;

    @Override
    public ParkingLogDto doCheckIn(ParkingLogDto parkingLogDto) {
        Establishment establishment = establishmentRepository.findById(parkingLogDto.establishmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Establishment not found with id: "
                        + parkingLogDto.establishmentId()));

        Vehicle vehicle = vehicleRepository.findByLicensePlate(parkingLogDto.licensePlate().toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with license plate: "
                        + parkingLogDto.licensePlate()));

        repository.findByEstablishmentUuidAndLicensePlateAndCheckOutIsNull(
                establishment.getUuid(),
                vehicle.getLicensePlate())
                .ifPresent(parkingLog -> {
                    throw new ResourceAlreadyExistsException(
                            "Vehicle already checked in at " + parkingLog.getCheckIn() + " with license plate "
                                    + parkingLog.getLicensePlate() + " in this establishment with id: "
                                    + parkingLog.getEstablishmentUuid());
                });

        repository.findByLicensePlateAndCheckOutIsNull(vehicle.getLicensePlate())
                .ifPresent(parkingLog -> {
                    throw new ResourceAlreadyExistsException(
                            "Vehicle with license plate " + parkingLog.getLicensePlate() + " already checked in at "
                                    + parkingLog.getCheckIn() + " in another establishment with id: "
                                    + parkingLog.getEstablishmentUuid());
                });

        if (VehicleTypeEnum.CAR.name().equals(vehicle.getType().name())
                && establishment.getAvailableSlotsCar() > 0)
            establishment.setAvailableSlotsCar(establishment.getAvailableSlotsCar() - 1);
        else if (VehicleTypeEnum.MOTORCYCLE.name().equals(vehicle.getType().name())
                && establishment.getAvailableSlotsMotorcycle() > 0)
            establishment.setAvailableSlotsMotorcycle(establishment.getAvailableSlotsMotorcycle() - 1);
        else
            throw new IllegalArgumentException("Not enough spaces for " + vehicle.getType().name());

        ParkingLog parkingLog = ParkingLog.builder()
                .establishmentUuid(establishment.getUuid())
                .licensePlate(vehicle.getLicensePlate())
                .type(vehicle.getType())
                .checkIn(LocalDateTime.now())
                .build();

        establishmentRepository.save(establishment);
        var savedParkingLog = repository.save(parkingLog);

        return ParkingLogDto.fromModel(savedParkingLog);
    }

    @Override
    public ParkingLogDto doCheckOut(Long id, ParkingLogDto parkingLogDto) {
        var establishment = establishmentRepository.findById(parkingLogDto.establishmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Establishment not found with id: "
                        + parkingLogDto.establishmentId()));

        var vehicle = vehicleRepository.findByLicensePlate(parkingLogDto.licensePlate().toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with license plate: "
                        + parkingLogDto.licensePlate()));

        ParkingLog parkingLog = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parking Log not found with id: " + id));

        if (parkingLog.getCheckOut() != null)
            throw new ResourceAlreadyExistsException("Vehicle with license plate " + vehicle.getLicensePlate()
                    + " already checked out at " + parkingLog.getCheckOut());

        if (VehicleTypeEnum.CAR.name().equals(vehicle.getType().name()))
            establishment.setAvailableSlotsCar(establishment.getAvailableSlotsCar() + 1);
        else if (VehicleTypeEnum.MOTORCYCLE.name().equals(vehicle.getType().name()))
            establishment.setAvailableSlotsMotorcycle(establishment.getAvailableSlotsMotorcycle() + 1);

        parkingLog.setCheckOut(LocalDateTime.now());

        establishmentRepository.save(establishment);
        var updatedParkingLog = repository.save(parkingLog);

        return ParkingLogDto.fromModel(updatedParkingLog);
    }
}
