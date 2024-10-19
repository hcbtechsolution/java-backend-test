package com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.model.Establishment;

public interface EstablishmentRepository extends JpaRepository<Establishment, String> {}
