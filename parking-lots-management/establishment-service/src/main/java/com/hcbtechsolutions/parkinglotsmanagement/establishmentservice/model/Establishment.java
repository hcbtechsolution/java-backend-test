package com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.model;

import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "parking_lots", name = "establishments")
public class Establishment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(length = 18, unique = true, nullable = false)
    private String cnpj;
    
    @Column(length = 100, nullable = false)
    private String name;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private Address address;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "phone_id", referencedColumnName = "id", nullable = false)
    private Phone phone;
    
    @Column(name = "number_space_motorcycle", nullable = false )
    private Integer numberSpaceMotorcycle;
    
    @Column(name = "number_space_car", nullable = false)
    private Integer numberSpaceCar;
}

