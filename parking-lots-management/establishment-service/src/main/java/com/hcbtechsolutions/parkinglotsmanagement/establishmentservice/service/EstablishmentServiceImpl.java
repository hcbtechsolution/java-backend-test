package com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

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
    public Establishment save(Establishment establishment) {
        log.info("Saving one Establishment!");

        String cnpj = establishment.getCnpj();
        Optional<Establishment> savedEstablishment = repository.findByCnpj(cnpj);

        if (savedEstablishment.isPresent())
            throw new ResourceAlreadyExistsException(ESTABLISHMENT_ALREADY_EXISTS + cnpj);

        return repository.save(establishment);
    }

    @Override
    public List<Establishment> findAll() {
        log.info("Finding all Establishments!");

        return repository.findAll();
    }

    @Override
    public Optional<Establishment> findOne(UUID id) {
        log.info("Finding one Establishment!");

        Optional<Establishment> establishment = repository.findById(id);
        
        if (establishment.isEmpty())
            throw new ResourceNotFoundException(ESTABLISHMENT_NOT_FOUND + id);
        
        return establishment;
    }

    @Override
    public Establishment update(UUID id, Establishment establishment) {
        log.info("Updating one Establishment!");

        return repository
            .findById(id)
            .map(foundEstablishment -> {
                foundEstablishment.setCnpj(establishment.getCnpj());
                foundEstablishment.setName(establishment.getName());

                foundEstablishment.getAddress().setName(establishment.getAddress().getName());
                foundEstablishment.getAddress().setNumber(establishment.getAddress().getNumber());
                foundEstablishment.getAddress().setComplement(establishment.getAddress().getComplement());
                foundEstablishment.getAddress().setDistrict(establishment.getAddress().getDistrict());
                foundEstablishment.getAddress().setCity(establishment.getAddress().getCity());
                foundEstablishment.getAddress().setState(establishment.getAddress().getState());
                foundEstablishment.getAddress().setCep(establishment.getAddress().getCep());

                foundEstablishment.getPhone().setDdd(establishment.getPhone().getDdd());
                foundEstablishment.getPhone().setNumber(establishment.getPhone().getNumber());

                foundEstablishment.setNumberSpaceMotocycle(establishment.getNumberSpaceMotocycle());
                foundEstablishment.setNumberSpaceCar(establishment.getNumberSpaceCar());
                return repository.save(foundEstablishment);
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
