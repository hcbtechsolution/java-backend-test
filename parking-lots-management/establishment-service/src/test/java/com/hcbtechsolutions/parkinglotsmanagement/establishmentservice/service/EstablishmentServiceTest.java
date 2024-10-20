package com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.service;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.dto.AddressDto;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.dto.EstablishmentDto;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.dto.PhoneDto;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.enums.StateEnum;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.exception.ResourceAlreadyExistsException;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.exception.ResourceNotFoundException;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.model.Establishment;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.repository.EstablishmentRepository;

@ExtendWith(MockitoExtension.class)
class EstablishmentServiceTest {

    @Mock
    private EstablishmentRepository repository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private EstablishmentServiceImpl service;

    private EstablishmentDto establishmentOne;

    @BeforeEach
    public void setup() {
        establishmentOne = new EstablishmentDto(
            null,
            "62.258.611/0001-91",
            "Establishment Test 1",
            new AddressDto(
                null, 
                "Rua Oiapoque", 
                "60", 
                "casa", 
                "Aleixo", 
                "Manaus", 
                StateEnum.AM, 
                "69060-170"),
            new PhoneDto(null, "92", "98199-5567"),
            6,
            6
        );
    }

    @Test
    @DisplayName("JUnit test for Given Establishment Object when Save then Return Saved Establishment")
    void testGivenEstablishmentObject_whenSave_thenReturnSavedEstablishment() {
        given(repository.findByCnpj(anyString())).willReturn(Optional.empty());
        given(repository.save(any(Establishment.class))).willReturn(establishmentOne.toModel());

        EstablishmentDto savedEstablishment = service.save(establishmentOne);

        assertNotNull(savedEstablishment);
        assertNotNull(savedEstablishment.address());
        assertNotNull(savedEstablishment.phone());
        assertEquals(establishmentOne.name(), savedEstablishment.name());
        assertEquals(establishmentOne.cnpj(), savedEstablishment.cnpj());
    }

    @Test
    @DisplayName("JUnit test for Given Existing Cnpj when Save then Trhows ResourceAlreadyExistsException")
    void testGivenExistingCnpj_whenSave_thenTrhowsResourceAlreadyExistsException() {
        given(repository.findByCnpj(anyString())).willReturn(Optional.of(establishmentOne.toModel()));
        
        assertThrows(ResourceAlreadyExistsException.class, () -> service.save(establishmentOne));
        
        verify(repository, never()).save(any(Establishment.class));
    }

    @Test
    @DisplayName("JUnit test for Given Establishment List when FindAll then Return Establishment List")
    void testGivenEstablishmentList_whenFindAll_thenReturnEstablishmentList() {
        given(repository.findAll()).willReturn(List.of(establishmentOne.toModel()));

        List<EstablishmentDto> establishmentList = service.findAll();

        assertNotNull(establishmentList);
        assertFalse(establishmentList.isEmpty());
        assertEquals(1, establishmentList.size());
    }

    @Test
    @DisplayName("JUnit test for Given Empty Establishment List when FindAll then Return Empty Establishment List")
    void testGivenEmptyEstablishmentList_whenFindAll_thenReturnEmptyEstablishmentList() {
        given(repository.findAll()).willReturn(Collections.emptyList());

        List<EstablishmentDto> establishmentList = service.findAll();

        assertTrue(establishmentList.isEmpty());
        assertEquals(0, establishmentList.size());
    }

    @Test
    @DisplayName("JUnit test for Given EstablishmentId when FindOne then Return Establishment Object")
    void testGivenEstablishmentId_whenFindOne_thenReturnEstablishmentObject() {
        UUID establishmentId = UUID.randomUUID();
        Establishment establishment = establishmentOne.toModel();
        establishment.setId(establishmentId);
        given(repository.findById(any(UUID.class))).willReturn(Optional.of(establishment));

        EstablishmentDto foundEstablishment = service.findOne(establishmentId);

        assertNotNull(foundEstablishment);
        assertEquals(establishmentId, foundEstablishment.id());
    }

    @Test
    @DisplayName("JUnit test for Given non Existent EstablishmentId when FindOne then Trhows ResourceNotFoundException")
    void testGivenNonExistentEstablishmentId_whenFindOne_thenTrhowsResourceNotFoundException() {
        UUID establishmentId = UUID.randomUUID();
        given(repository.findById(any(UUID.class))).willReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findOne(establishmentId));
    }

    @Test
    @DisplayName("JUnit test for Given EstablishmentObject when Update then Return UpdatedEstablishment")
    void testGivenEstablishmentObject_whenUpdate_thenReturnUpdatedEstablishment() {
        UUID establishmentId = UUID.randomUUID();
        String updatedName = "Establishment Test 1 Updated";
        Integer updatedNumberSpaceCar = 3;
        
        Establishment establishment = establishmentOne.toModel();
        
        establishment.setId(establishmentId);
        establishment.setName(updatedName);
        establishment.setNumberSpaceCar(updatedNumberSpaceCar);

        EstablishmentDto establishmentDto = EstablishmentDto.fromModel(establishment);
        
        given(repository.findById(any(UUID.class))).willReturn(Optional.of(establishment));
        given(repository.save(any(Establishment.class))).willReturn(establishment);

        EstablishmentDto updatedEstablishment = service.update(establishmentId, establishmentDto);
        
        assertNotNull(updatedEstablishment);
        assertEquals(updatedName, updatedEstablishment.name());
        assertEquals(updatedNumberSpaceCar, updatedEstablishment.numberSpaceCar());
    }

    @Test
    @DisplayName("JUnit test for Given non Existent EstablishmentId when Update then Trhows ResourceNotFoundException")
    void testGivenNonExistentEstablishmentId_whenUpdate_thenTrhowsResourceNotFoundException() {
        UUID establishmentId = UUID.randomUUID();
        given(repository.findById(any(UUID.class))).willReturn(Optional.empty());

        assertThrows(
            ResourceNotFoundException.class, 
            () -> service.update(establishmentId, establishmentOne));
        
        verify(repository, never()).save(any(Establishment.class));
    }

    @Test
    @DisplayName("JUnit test for Given EstablishmentId when Delete then Do Nothing")
    void testGivenEstablishmentId_whenDelete_thenDoNothing() {
        UUID establishmentId = UUID.randomUUID();
        given(repository.existsById(any(UUID.class))).willReturn(Boolean.TRUE);
        willDoNothing().given(repository).deleteById(establishmentId);

        service.delete(establishmentId);

        verify(repository, times(1)).deleteById(establishmentId);
    }
}
