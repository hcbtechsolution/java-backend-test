package com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.model.ParkingLog;

public interface ParkingLogRepository extends JpaRepository<ParkingLog, Long> {

    Optional<ParkingLog> findByEstablishmentUuidAndLicensePlateAndCheckOutIsNull(String establishmentUuid,
            String licensePlate);

    Optional<ParkingLog> findByLicensePlateAndCheckOutIsNull(String licensePlate);

    Optional<ParkingLog> findByIdAndCheckOutIsNotNull(Long id);
}
