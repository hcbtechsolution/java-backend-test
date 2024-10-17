package com.hcbtechsolutions.parkinglotsmanagement.vehicleservice.controller;

import java.util.List;

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
@RequestMapping("/vehicles")
public class VehicleController {

    private VehicleService service;

    @PostMapping(
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<VehicleDto> postVehicle(@RequestBody VehicleDto vehicle) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(vehicle));
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<VehicleDto>> getAllVehicles() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<VehicleDto> getOneVehicle(@PathVariable String id) {
        return ResponseEntity.ok(service.findOne(id));
    }

    @PutMapping(
        value = "/{id}",
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<VehicleDto> putVehicle(
        @PathVariable String id, @RequestBody VehicleDto vehicle) {
        return ResponseEntity.ok(service.update(id, vehicle));
    }

    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> deleteVehicle(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
