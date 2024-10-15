package com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.model;

import java.util.UUID;

import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.enums.StateEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(schema = "parking_lots", name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(length = 100, nullable = false)
    private String name;
    
    @Column(length = 10, nullable = false)
    private String number;
    
    @Column(length = 60)
    private String complement;
    
    @Column(length = 60, nullable = false)
    private String district;
    
    @Column(length = 60, nullable = false)
    private String city;
    
    @Column(length = 2, nullable = false)
    @Enumerated(EnumType.STRING)
    private StateEnum state;
    
    @Column(length = 9, nullable = false)
    private String cep;
}
