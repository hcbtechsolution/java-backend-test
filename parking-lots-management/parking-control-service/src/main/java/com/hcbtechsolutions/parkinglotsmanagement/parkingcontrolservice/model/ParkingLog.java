package com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.model;

import java.time.LocalDateTime;

import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.enums.VehicleTypeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "parking_logs")
public class ParkingLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "establishment_uuid", length = 36, unique = true, nullable = false)
    private String establishmentUuid;

    @Column(name = "license_plate", length = 8, unique = true, nullable = false)
    private String licensePlate;

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleTypeEnum type;

    @Column(name = "check_in", nullable = false)
    private LocalDateTime checkIn;

    @Column(name = "check_out")
    private LocalDateTime checkOut;
}
