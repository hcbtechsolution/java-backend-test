package com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "establishments")
public class Establishment {

    @Id
    @Column(length = 36, unique = true, nullable = false)
    private String uuid;
    
    @Column(length = 18, unique = true, nullable = false)
    private String cnpj;
    
    @Column(name = "available_slots_car", nullable = false )
    private Integer availableSlotsCar;
    
    @Column(name = "available_slots_motorcycle", nullable = false )
    private Integer availableSlotsMotorcycle;
    
    @Column(name = "total_slots_car", nullable = false )
    private Integer totalSlotsCar;
    
    @Column(name = "total_slots_motorcycle", nullable = false )
    private Integer totalSlotsMotorcycle;
}
