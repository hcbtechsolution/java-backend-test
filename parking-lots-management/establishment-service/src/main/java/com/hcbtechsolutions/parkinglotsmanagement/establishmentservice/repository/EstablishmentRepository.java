package com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.model.Establishment;

public interface EstablishmentRepository extends JpaRepository<Establishment, UUID> {
    Optional<Establishment> findByCnpj(String cnpj);
}
