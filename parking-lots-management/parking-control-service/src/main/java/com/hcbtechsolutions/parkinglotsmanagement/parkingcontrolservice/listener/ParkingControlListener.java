package com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.config.RabbitConfig;
import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.dto.EstablishmentMessageDto;
import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.dto.VehicleMessageDto;
import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.model.Establishment;
import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.model.Vehicle;
import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.repository.EstablishmentRepository;
import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.repository.VehicleRepository;

@Component
public class ParkingControlListener {

    private VehicleRepository vehicleRepository;
    private EstablishmentRepository establishmentRepository;

    public ParkingControlListener(VehicleRepository vehicleRepository, EstablishmentRepository establishmentRepository) {
        this.vehicleRepository = vehicleRepository;
        this.establishmentRepository = establishmentRepository;
    }

    @RabbitListener(queues = RabbitConfig.VEHICLE_QUEUE)
    public void receiveVehicleMessage(VehicleMessageDto vehicleMessage) {
        Vehicle vehicle = vehicleMessage.toModel();
        vehicleRepository.save(vehicle);
    }

    @RabbitListener(queues = RabbitConfig.ESTABELECIMENTO_QUEUE)
    public void receiveEstablishmentMessage(EstablishmentMessageDto establishmentMessage) {
        establishmentRepository.findById(establishmentMessage.uuid())
            .ifPresentOrElse(
                establishment -> {
                    establishment.setCnpj(establishmentMessage.cnpj());

                    updateSlotAvailability(establishment, establishmentMessage);

                    establishmentRepository.save(establishment);
                }, 
                () -> {
                    Establishment establishment = establishmentMessage.toModel();

                    establishment.setAvailableSlotsCar(establishmentMessage.totalSlotsCar());
                    establishment.setAvailableSlotsMotorcycle(establishmentMessage.totalSlotsMotorcycle());
                    
                    establishmentRepository.save(establishment);
                });        
    }

    private void updateSlotAvailability(Establishment establishment, EstablishmentMessageDto establishmentMessage) {
        int variationSlotsCar = establishmentMessage.totalSlotsCar() - establishment.getTotalSlotsCar();
        int variationSlotsMotorcycle = establishmentMessage.totalSlotsMotorcycle() - establishment.getTotalSlotsMotorcycle();

        if (variationSlotsCar >= 0) {
            establishment.setAvailableSlotsCar(establishment.getAvailableSlotsCar() + variationSlotsCar);
            establishment.setTotalSlotsCar(establishmentMessage.totalSlotsCar());
        } else if (establishment.getAvailableSlotsCar() >= variationSlotsCar) {
            establishment.setAvailableSlotsCar(establishment.getAvailableSlotsCar() - variationSlotsCar);
            establishment.setTotalSlotsCar(establishmentMessage.totalSlotsCar());
        } else {
            throw new IllegalArgumentException("Not enough spaces");
        }

        if (variationSlotsMotorcycle >= 0) {
            establishment.setAvailableSlotsMotorcycle(establishment.getAvailableSlotsMotorcycle() + variationSlotsMotorcycle);
            establishment.setTotalSlotsMotorcycle(establishmentMessage.totalSlotsMotorcycle());
        } else if (establishment.getAvailableSlotsMotorcycle() >= variationSlotsMotorcycle) {
            establishment.setAvailableSlotsMotorcycle(establishment.getAvailableSlotsMotorcycle() - variationSlotsMotorcycle);
            establishment.setTotalSlotsMotorcycle(establishmentMessage.totalSlotsMotorcycle());
        } else {
            throw new IllegalArgumentException("Not enough spaces");
        }
    }
}
