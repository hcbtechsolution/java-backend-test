package com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.service;

import com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.dto.ParkingLogDto;

public interface ParkingControlService {

    ParkingLogDto doCheckIn(ParkingLogDto parkingLog);

    ParkingLogDto doCheckOut(Long id, ParkingLogDto parkingLog);
}
