package com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {
}
