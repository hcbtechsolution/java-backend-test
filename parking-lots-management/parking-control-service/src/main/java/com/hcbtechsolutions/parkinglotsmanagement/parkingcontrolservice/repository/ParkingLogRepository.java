package com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.model.ParkingLog;

public interface ParkingLogRepository extends JpaRepository<ParkingLog, Long> {

}
