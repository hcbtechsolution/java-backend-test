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

import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.enums.StateEnum;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.exception.ResourceAlreadyExistsException;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.exception.ResourceNotFoundException;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.model.Address;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.model.Establishment;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.model.Phone;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.repository.EstablishmentRepository;

@ExtendWith(MockitoExtension.class)
class EstablishmentServiceTest {

    @Mock
    private EstablishmentRepository repository;

    @InjectMocks
    private EstablishmentServiceImpl service;

    private Establishment establishmentOne;

    @BeforeEach
    public void setup() {
        establishmentOne = Establishment.builder()
                .cnpj("62.258.611/0001-91")
                .name("Establishment Test 1")
                .address(
                    Address.builder()
                        .name("Rua Oiapoque")
                        .number("60")
                        .complement("casa")
                        .district("Aleixo")
                        .city("Manaus")
                        .state(StateEnum.AM)
                        .cep("69060-170")
                        .build())
                .phone(
                    Phone.builder()
                        .ddd("92")
                        .number("98199-5567")
                        .build())
                .numberSpaceCar(6)
                .numberSpaceMotocycle(6)
                .build();
    }

    @Test
    @DisplayName("JUnit test for Given Establishment Object when Save then Return Saved Establishment")
    void testGivenEstablishmentObject_whenSave_thenReturnSavedEstablishment() {
        given(repository.findByCnpj(anyString())).willReturn(Optional.empty());
        given(repository.save(establishmentOne)).willReturn(establishmentOne);

        Establishment savedEstablishment = service.save(establishmentOne);

        assertNotNull(savedEstablishment);
        assertNotNull(savedEstablishment.getAddress());
        assertNotNull(savedEstablishment.getPhone());
        assertEquals(establishmentOne.getName(), savedEstablishment.getName());
        assertEquals(establishmentOne.getCnpj(), savedEstablishment.getCnpj());
    }

    @Test
    @DisplayName("JUnit test for Given Existing Cnpj when Save then Trhows ResourceAlreadyExistsException")
    void testGivenExistingCnpj_whenSave_thenTrhowsResourceAlreadyExistsException() {
        given(repository.findByCnpj(anyString())).willReturn(Optional.of(establishmentOne));
        
        assertThrows(ResourceAlreadyExistsException.class, () -> service.save(establishmentOne));
        
        verify(repository, never()).save(any(Establishment.class));
    }

    @Test
    @DisplayName("JUnit test for Given Establishment List when FindAll then Return Establishment List")
    void testGivenEstablishmentList_whenFindAll_thenReturnEstablishmentList() {
        given(repository.findAll()).willReturn(List.of(establishmentOne));

        List<Establishment> establishmentList = service.findAll();

        assertNotNull(establishmentList);
        assertFalse(establishmentList.isEmpty());
        assertEquals(1, establishmentList.size());
    }

    @Test
    @DisplayName("JUnit test for Given Empty Establishment List when FindAll then Return Empty Establishment List")
    void testGivenEmptyEstablishmentList_whenFindAll_thenReturnEmptyEstablishmentList() {
        given(repository.findAll()).willReturn(Collections.emptyList());

        List<Establishment> establishmentList = service.findAll();

        assertTrue(establishmentList.isEmpty());
        assertEquals(0, establishmentList.size());
    }

    @Test
    @DisplayName("JUnit test for Given EstablishmentId when FindOne then Return Establishment Object")
    void testGivenEstablishmentId_whenFindOne_thenReturnEstablishmentObject() {
        UUID establishmentId = UUID.randomUUID();
        establishmentOne.setId(establishmentId);
        given(repository.findById(any(UUID.class))).willReturn(Optional.of(establishmentOne));

        Optional<Establishment> foundEstablishment = service.findOne(establishmentId);

        assertTrue(foundEstablishment.isPresent());
        assertEquals(establishmentId, foundEstablishment.get().getId());
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
        establishmentOne.setId(establishmentId);
        
        given(repository.findById(any(UUID.class))).willReturn(Optional.of(establishmentOne));
        
        String updatedName = "Establishment Test 1 Updated";
        Integer updatedNumberSpaceCar = 3;

        establishmentOne.setName(updatedName);
        establishmentOne.setNumberSpaceCar(updatedNumberSpaceCar);

        given(repository.save(establishmentOne)).willReturn(establishmentOne);

        Establishment updatedEstablishment = service.update(establishmentId, establishmentOne);
        
        assertNotNull(updatedEstablishment);
        assertEquals(updatedName, updatedEstablishment.getName());
        assertEquals(updatedNumberSpaceCar, updatedEstablishment.getNumberSpaceCar());
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
