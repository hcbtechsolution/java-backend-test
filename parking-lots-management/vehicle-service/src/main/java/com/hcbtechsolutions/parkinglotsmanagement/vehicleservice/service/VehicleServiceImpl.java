package com.hcbtechsolutions.parkinglotsmanagement.vehicleservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.hcbtechsolutions.parkinglotsmanagement.vehicleservice.config.RabbitConfig;
import com.hcbtechsolutions.parkinglotsmanagement.vehicleservice.dto.VehicleDto;
import com.hcbtechsolutions.parkinglotsmanagement.vehicleservice.dto.VehicleMessageDto;
import com.hcbtechsolutions.parkinglotsmanagement.vehicleservice.exception.ResourceAlreadyExistsException;
import com.hcbtechsolutions.parkinglotsmanagement.vehicleservice.exception.ResourceNotFoundException;
import com.hcbtechsolutions.parkinglotsmanagement.vehicleservice.model.Vehicle;
import com.hcbtechsolutions.parkinglotsmanagement.vehicleservice.repository.VehicleRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@Service
public class VehicleServiceImpl implements VehicleService {

    private VehicleRepository repository;
    private RabbitTemplate rabbitTemplate;

    private static final String VEHICLE_NOT_FOUND = "Vehicle not found with id: ";
    private static final String VEHICLE_ALREADY_EXISTS = "Vehicle already exists with license plate: ";

    @Override
    public VehicleDto save(VehicleDto vehicle) {
        log.info("Saving one Vehicle!");

        String licensePlate = vehicle.licensePlate();
        Optional<Vehicle> optionalVehicle = repository.findByLicensePlate(licensePlate);

        if (optionalVehicle.isPresent())
            throw new ResourceAlreadyExistsException(VEHICLE_ALREADY_EXISTS + licensePlate);

        Vehicle savedVehicle = repository.save(vehicle.toModel());

        VehicleMessageDto vehicleMessageDto = VehicleMessageDto.fromModel(savedVehicle);

        rabbitTemplate.convertAndSend(
            RabbitConfig.TOPIC_EXCHANGE, 
            RabbitConfig.VEHICLE_ROUTING_KEY, 
            vehicleMessageDto);

        return VehicleDto.fromModel(savedVehicle);        
    }
    @Override
    public List<VehicleDto> findAll() {
        log.info("Finding all Vehicles!");

        return repository.findAll()
            .stream().map(
                VehicleDto::fromModel
            ).collect(
                Collectors.toList()
            );
    }
    @Override
    public VehicleDto findOne(String id) {
        log.info("Finding one Vehicle!");

        Vehicle vehicle = repository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException(VEHICLE_NOT_FOUND + id)
        );

        return VehicleDto.fromModel(vehicle);
    }
    @Override
    public VehicleDto update(String id, VehicleDto vehicle) {
        log.info("Updating one Vehicle!");

        return repository
            .findById(id)
            .map(foundVehicle -> {
                foundVehicle.setBrand(vehicle.brand());
                foundVehicle.setModel(vehicle.model());
                foundVehicle.setColor(vehicle.color());
                foundVehicle.setLicensePlate(vehicle.licensePlate());
                foundVehicle.setType(vehicle.type());

                Vehicle updatedVehicle = repository.save(foundVehicle);

                VehicleMessageDto vehicleMessageDto = VehicleMessageDto.fromModel(foundVehicle);

                rabbitTemplate.convertAndSend(
                    RabbitConfig.TOPIC_EXCHANGE, 
                    RabbitConfig.VEHICLE_ROUTING_KEY, 
                    vehicleMessageDto);
                    
                return VehicleDto.fromModel(updatedVehicle);
            })
            .orElseThrow(
                () -> new ResourceNotFoundException(VEHICLE_NOT_FOUND + id)
            );
    }
    @Override
    public void delete(String id) {
        log.info("Deleting one Vehicle!");

        if (repository.existsById(id))
            repository.deleteById(id);            
        else
            throw new ResourceNotFoundException(VEHICLE_NOT_FOUND + id);
    }
}
