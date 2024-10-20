package com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {

    Optional<Vehicle> findByLicensePlate(String licensePlate);
}
