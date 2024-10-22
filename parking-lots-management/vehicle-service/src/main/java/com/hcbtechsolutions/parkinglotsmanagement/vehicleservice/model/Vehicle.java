package com.hcbtechsolutions.parkinglotsmanagement.vehicleservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.hcbtechsolutions.parkinglotsmanagement.vehicleservice.enums.VehicleTypeEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "vehicles")
public class Vehicle {
    @Id
    private String id;

    @NotBlank(message = "Brand is mandatory")
    @Size(min = 2, max = 50, message = "Brand must be between 2 and 50 characters")
    private String brand;

    @NotBlank(message = "Model is mandatory")
    @Size(min = 1, max = 50, message = "Model must be between 1 and 50 characters")
    private String model;

    @NotBlank(message = "Color is mandatory")
    @Size(min = 1, max = 20, message = "Model must be between 1 and 20 characters")
    private String color;

    @NotBlank(message = "License plate is mandatory")
    @Pattern(regexp = "^[A-Z]{3}-(\\d{4}|\\d[A-Z]\\d{2})$", message = "License plate must follow the pattern ABC-1234 or ABC-1A34")
    @Indexed(unique = true)
    @Field("license_plate")
    private String licensePlate;

    @NotNull(message = "Vehicle type is mandatory")
    private VehicleTypeEnum type;
}
