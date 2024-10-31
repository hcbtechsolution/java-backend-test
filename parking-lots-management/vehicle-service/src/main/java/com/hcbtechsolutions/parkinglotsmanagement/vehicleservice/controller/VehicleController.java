package com.hcbtechsolutions.parkinglotsmanagement.vehicleservice.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcbtechsolutions.parkinglotsmanagement.vehicleservice.dto.VehicleDto;
import com.hcbtechsolutions.parkinglotsmanagement.vehicleservice.service.VehicleService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/vehicles", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class VehicleController {

    private static final String VEHICLES = "vehicles";
    private VehicleService service;

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<EntityModel<VehicleDto>> postVehicle(@RequestBody VehicleDto vehicleDto) {
        VehicleDto vehicle = service.save(vehicleDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(EntityModel.of(
                        vehicle,
                        linkTo(methodOn(VehicleController.class).getOneVehicle(vehicle.id())).withSelfRel(),
                        linkTo(methodOn(VehicleController.class).getAllVehicles()).withRel(VEHICLES)));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<VehicleDto>>> getAllVehicles() {
        var vehicleModels = service
                .findAll()
                .stream()
                .map(dto -> EntityModel.of(
                        dto,
                        linkTo(methodOn(VehicleController.class).getOneVehicle(dto.id())).withSelfRel()))
                .collect(Collectors.toList());

        return ResponseEntity
                .ok(CollectionModel.of(
                        vehicleModels,
                        linkTo(methodOn(VehicleController.class).getAllVehicles()).withSelfRel()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EntityModel<VehicleDto>> getOneVehicle(@PathVariable String id) {
        return ResponseEntity
                .ok(EntityModel.of(
                        service.findOne(id),
                        linkTo(methodOn(VehicleController.class).getOneVehicle(id)).withSelfRel(),
                        linkTo(methodOn(VehicleController.class).getAllVehicles()).withRel(VEHICLES)));
    }

    @PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<EntityModel<VehicleDto>> putVehicle(
            @PathVariable String id,
            @RequestBody VehicleDto vehicleDto) {
        return ResponseEntity
                .ok(EntityModel.of(
                        service.update(id, vehicleDto),
                        linkTo(methodOn(VehicleController.class).getOneVehicle(id)).withSelfRel(),
                        linkTo(methodOn(VehicleController.class).getAllVehicles()).withRel(VEHICLES)));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
