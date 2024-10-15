package com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.model.Establishment;

public interface EstablishmentService {

    Establishment save(Establishment establishment);
    List<Establishment> findAll();
    Optional<Establishment> findOne(UUID id);
    Establishment update(UUID id, Establishment establishment);
    void delete(UUID id);
}
