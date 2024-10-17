package com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.contextinitializer.AbstractApplicationContextInitializer;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.enums.StateEnum;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.model.Address;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.model.Establishment;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.model.Phone;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EstablishmentRepositoryTest extends AbstractApplicationContextInitializer {

    @Autowired
    private EstablishmentRepository repository;

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
                .numberSpaceMotorcycle(6)
                .build();
    }

    @Test
    @DisplayName("JUnit test for Given Establishment Object when Save then Return Saved Establishment")
    void testGivenEstablishmentObject_whenSave_thenReturnSavedEstablishment() {
        
        Establishment savedEstablishment = repository.save(establishmentOne);

        assertNotNull(savedEstablishment);
        assertNotNull(savedEstablishment.getId());
        assertNotNull(savedEstablishment.getAddress());
        assertNotNull(savedEstablishment.getAddress().getId());
        assertNotNull(savedEstablishment.getPhone());
        assertNotNull(savedEstablishment.getPhone().getId());
        assertEquals(establishmentOne.getName(), savedEstablishment.getName());
        assertEquals(establishmentOne.getCnpj(), savedEstablishment.getCnpj());
    }
    
    @DisplayName("JUnit test for Given Establishment List when findAll then Return Establishment List")
    @Test
    void testGivenEstablishmentList_whenFindAll_thenReturnEstablishmentList() {
        var establishmentTwo = Establishment.builder()
                .cnpj("39.967.491/0001-80")
                .name("Establishment Test 2")
                .address(
                    Address.builder()
                        .name("Rua Atagamita")
                        .number("180")
                        .complement("Ap 02")
                        .district("Aleixo")
                        .city("Manaus")
                        .state(StateEnum.AM)
                        .cep("69060-050")
                        .build())
                .phone(
                    Phone.builder()
                        .ddd("92")
                        .number("98498-5948")
                        .build())
                .numberSpaceCar(3)
                .numberSpaceMotorcycle(3)
                .build();
        
        repository.save(establishmentOne);
        repository.save(establishmentTwo);

        List<Establishment> list = repository.findAll();
        
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertEquals(2, list.size());
    }

    @Test
    @DisplayName("JUnit test for Given EstablishmentId when findById then Return Establishment Object")
    void testGivenEstablishmentId_whenFindById_thenReturnEstablishmentObject() {
        
        repository.save(establishmentOne);

        Optional<Establishment> optionalEstablishment = repository.findById(establishmentOne.getId());

        assertTrue(optionalEstablishment.isPresent());
        assertEquals(establishmentOne.getId(), optionalEstablishment.get().getId());
        assertEquals(establishmentOne.getName(), optionalEstablishment.get().getName());
        assertEquals(establishmentOne.getCnpj(), optionalEstablishment.get().getCnpj());
    }

    @Test
    @DisplayName("JUnit test for Given non Existent EstablishmentId when findById then Return No Record")
    void testGivenNonExistentEstablishmentId_whenFindById_thenReturnNoRecord() {
        
        UUID nonExistentId = UUID.randomUUID();
        
        Optional<Establishment> optionalEstablishment = repository.findById(nonExistentId);
        
        assertTrue(optionalEstablishment.isEmpty());
    }
    
    @Test
    @DisplayName("JUnit test for Given Establishment Object when Update then Return Updated Establishment")
    void testGivenEstablishmentObject_whenUpdate_thenReturnUpdatedEstablishment() {
        
        repository.save(establishmentOne);
        
        Establishment foundEstablishment = repository.findById(establishmentOne.getId()).get();
        
        foundEstablishment.setName("Establishment Test 1 Updated");
        
        Establishment updatedEstablishment = repository.save(establishmentOne);
        
        assertNotNull(updatedEstablishment);
        assertEquals(foundEstablishment.getName(), updatedEstablishment.getName());
    }

    @Test
    @DisplayName("JUnit test for Given EstablishmentId when Delete then Return Deleted Establishment")
    void testGivenEstablishmentId_whenDelete_thenReturnDeletedEstablishment() {
        
        repository.save(establishmentOne);
        
        Establishment foundEstablishment = repository.findById(establishmentOne.getId()).get();
        
        repository.deleteById(foundEstablishment.getId());
        
        Optional<Establishment> optionalEstablishment = repository.findById(foundEstablishment.getId());
        
        assertTrue(optionalEstablishment.isEmpty());
    }
}