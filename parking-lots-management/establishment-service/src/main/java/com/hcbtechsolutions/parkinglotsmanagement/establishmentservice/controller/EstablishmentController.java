package com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.controller;

import java.util.List;
import java.util.UUID;

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

import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.dto.EstablishmentDto;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.service.EstablishmentService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/establishments")
public class EstablishmentController {

    private EstablishmentService service;

    @PostMapping(
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<EstablishmentDto> postEstablishment(@RequestBody EstablishmentDto establishment) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(establishment));
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<EstablishmentDto>> getAllEstablishments() {
        return ResponseEntity.ok(service.findAll());
    }
    
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<EstablishmentDto> getOneEstablishment(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findOne(id));
    }

    @PutMapping(
        value = "/{id}",
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<EstablishmentDto> putEstablishment(
        @PathVariable UUID id, @RequestBody EstablishmentDto establishment) {
        return ResponseEntity.ok(service.update(id, establishment));
    }

    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> deleteEstablishment(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
