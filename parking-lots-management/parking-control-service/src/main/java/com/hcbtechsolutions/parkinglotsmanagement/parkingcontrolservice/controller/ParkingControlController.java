package com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.dto.ParkingLogDto;
import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.service.ParkingControlService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/parking-control", produces = { MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_XML_VALUE })
public class ParkingControlController {

    private ParkingControlService service;

    @PostMapping(value = "/checkin", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<EntityModel<ParkingLogDto>> checkin(@RequestBody ParkingLogDto parkingLogDto) {
        var parkingLog = service.doCheckIn(parkingLogDto);

        return ResponseEntity.ok(
                EntityModel.of(
                        parkingLog,
                        linkTo(methodOn(ParkingControlController.class).checkout(parkingLog.id(), parkingLogDto))
                                .withRel("checkout")));
    }

    @PutMapping(value = "/checkout/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<ParkingLogDto> checkout(@PathVariable Long id, @RequestBody ParkingLogDto parkingLog) {
        return ResponseEntity.ok(service.doCheckOut(id, parkingLog));
    }
}
