package com.hcbtechsolutions.parkinglotsmanagement.vehicleservice.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.hcbtechsolutions.parkinglotsmanagement.vehicleservice.model.Vehicle;

public interface VehicleRepository extends MongoRepository<Vehicle, String> {
    Optional<Vehicle> findByLicensePlate(String licensePlate);
}
