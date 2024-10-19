package com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.model;

import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.enums.VehicleTypeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    private String id;
    
    @Column(name = "license_plate", length = 8, unique = true, nullable = false)
    @Pattern(
        regexp = "^[A-Z]{3}-(\\d{4}|\\d[A-Z]\\d{2})$", 
        message = "License plate must follow the pattern ABC-1234 or ABC-1A34")
    private String licensePlate;
    
    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleTypeEnum type;
}
