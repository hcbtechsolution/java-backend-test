package com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.UUID;
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

import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.dto.EstablishmentDto;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.service.EstablishmentService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/establishments", produces = { MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_XML_VALUE })
public class EstablishmentController {

    private static final String ESTABLISHMENTS = "establishments";

    private EstablishmentService service;

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<EntityModel<EstablishmentDto>> postEstablishment(
            @RequestBody EstablishmentDto establishmentDto) {
        EstablishmentDto establishment = service.save(establishmentDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(EntityModel.of(
                        establishment,
                        linkTo(methodOn(EstablishmentController.class).getOneEstablishment(establishment.id()))
                                .withSelfRel(),
                        linkTo(methodOn(EstablishmentController.class).getAllEstablishments())
                                .withRel(ESTABLISHMENTS)));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<EstablishmentDto>>> getAllEstablishments() {
        var establishmentModels = service
                .findAll()
                .stream()
                .map(dto -> EntityModel.of(
                        dto,
                        linkTo(methodOn(EstablishmentController.class).getOneEstablishment(dto.id())).withSelfRel()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(
                CollectionModel.of(
                        establishmentModels,
                        linkTo(methodOn(EstablishmentController.class).getAllEstablishments()).withSelfRel()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EntityModel<EstablishmentDto>> getOneEstablishment(@PathVariable UUID id) {
        return ResponseEntity.ok(
                EntityModel.of(
                        service.findOne(id),
                        linkTo(methodOn(EstablishmentController.class).getOneEstablishment(id)).withSelfRel(),
                        linkTo(methodOn(EstablishmentController.class).getAllEstablishments())
                                .withRel(ESTABLISHMENTS)));
    }

    @PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<EntityModel<EstablishmentDto>> putEstablishment(
            @PathVariable UUID id,
            @RequestBody EstablishmentDto establishmentDto) {
        return ResponseEntity.ok(
                EntityModel.of(
                        service.update(id, establishmentDto),
                        linkTo(methodOn(EstablishmentController.class).getOneEstablishment(id)).withSelfRel(),
                        linkTo(methodOn(EstablishmentController.class).getAllEstablishments())
                                .withRel(ESTABLISHMENTS)));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteEstablishment(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
