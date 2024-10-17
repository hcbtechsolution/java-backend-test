package com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.dto.EstablishmentDto;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.exception.ResourceAlreadyExistsException;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.exception.ResourceNotFoundException;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.model.Establishment;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.repository.EstablishmentRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@Service
public class EstablishmentServiceImpl implements EstablishmentService {

    private EstablishmentRepository repository;
    
    private static final String ESTABLISHMENT_NOT_FOUND = "Establishment not found with id: ";
    private static final String ESTABLISHMENT_ALREADY_EXISTS = "Establishment already exists with cnpj: ";

    @Override
    public EstablishmentDto save(EstablishmentDto establishment) {
        log.info("Saving one Establishment!");

        String cnpj = establishment.cnpj();
        Optional<Establishment> optionalEstablishment = repository.findByCnpj(cnpj);

        if (optionalEstablishment.isPresent())
            throw new ResourceAlreadyExistsException(ESTABLISHMENT_ALREADY_EXISTS + cnpj);

        Establishment savadEstablishment = repository.save(establishment.toModel());

        return EstablishmentDto.fromModel(savadEstablishment);
    }

    @Override
    public List<EstablishmentDto> findAll() {
        log.info("Finding all Establishments!");

        return repository.findAll()
            .stream().map(
                EstablishmentDto::fromModel
            ).collect(
                Collectors.toList()
            );
    }

    @Override
    public EstablishmentDto findOne(UUID id) {
        log.info("Finding one Establishment!");

        Establishment establishment = repository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException(ESTABLISHMENT_NOT_FOUND + id)
        );
        
        return EstablishmentDto.fromModel(establishment);
    }

    @Override
    public EstablishmentDto update(UUID id, EstablishmentDto establishment) {
        log.info("Updating one Establishment!");

        return repository
            .findById(id)
            .map(foundEstablishment -> {
                foundEstablishment.setCnpj(establishment.cnpj());
                foundEstablishment.setName(establishment.name());

                foundEstablishment.getAddress().setName(establishment.address().name());
                foundEstablishment.getAddress().setNumber(establishment.address().number());
                foundEstablishment.getAddress().setComplement(establishment.address().complement());
                foundEstablishment.getAddress().setDistrict(establishment.address().district());
                foundEstablishment.getAddress().setCity(establishment.address().city());
                foundEstablishment.getAddress().setState(establishment.address().state());
                foundEstablishment.getAddress().setCep(establishment.address().cep());

                foundEstablishment.getPhone().setDdd(establishment.phone().ddd());
                foundEstablishment.getPhone().setNumber(establishment.phone().number());

                foundEstablishment.setNumberSpaceMotorcycle(establishment.numberSpaceMotorcycle());
                foundEstablishment.setNumberSpaceCar(establishment.numberSpaceCar());

                Establishment updatedEstablishment = repository.save(foundEstablishment);

                return EstablishmentDto.fromModel(updatedEstablishment);
            })
            .orElseThrow(() -> new ResourceNotFoundException(ESTABLISHMENT_NOT_FOUND + id));
    }

    @Override
    public void delete(UUID id) {
        log.info("Deleting one Establishment!");

        if (repository.existsById(id))
            repository.deleteById(id);            
        else
            throw new ResourceNotFoundException(ESTABLISHMENT_NOT_FOUND + id);
    }    
}
