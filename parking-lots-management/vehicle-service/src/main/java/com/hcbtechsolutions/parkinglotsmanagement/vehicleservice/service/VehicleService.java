package com.hcbtechsolutions.parkinglotsmanagement.vehicleservice.service;

import java.util.List;

import com.hcbtechsolutions.parkinglotsmanagement.vehicleservice.dto.VehicleDto;

public interface VehicleService {

    VehicleDto save(VehicleDto vehicle);
    List<VehicleDto> findAll();
    VehicleDto findOne(String id);
    VehicleDto update(String id, VehicleDto vehicle);
    void delete(String id);
}
