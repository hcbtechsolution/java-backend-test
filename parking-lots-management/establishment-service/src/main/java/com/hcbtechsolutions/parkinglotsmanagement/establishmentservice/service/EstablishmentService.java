package com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.service;

import java.util.List;
import java.util.UUID;

import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.dto.EstablishmentDto;

public interface EstablishmentService {

    EstablishmentDto save(EstablishmentDto establishment);
    List<EstablishmentDto> findAll();
    EstablishmentDto findOne(UUID id);
    EstablishmentDto update(UUID id, EstablishmentDto establishment);
    void delete(UUID id);
}
