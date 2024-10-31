package com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.dto.AddressDto;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.dto.EstablishmentDto;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.dto.PhoneDto;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.enums.StateEnum;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.exception.ResourceAlreadyExistsException;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.exception.ResourceNotFoundException;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.model.Establishment;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.service.EstablishmentServiceImpl;

@WebMvcTest
class EstablishmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private EstablishmentServiceImpl service;

    private EstablishmentDto establishmentDto;

    private static final String ESTABLISHMENT_BASE_URL = "/establishments";

    @BeforeEach
    public void setup() {
        establishmentDto = new EstablishmentDto(
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
                6);
    }

    @Test
    @DisplayName("JUnit test for Given EstablishmentJson when Save then Return SavedEstablishment")
    void testGivenEstablishmentJson_whenSave_thenReturnSavedEstablishment() throws Exception {
        given(service.save(any(EstablishmentDto.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post(ESTABLISHMENT_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(establishmentDto)));

        response
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(establishmentDto.name())))
                .andExpect(jsonPath("$.numberSpaceCar", is(establishmentDto.numberSpaceCar())))
                .andDo(print());
    }

    @Test
    @DisplayName("JUnit test for Given an existing cnpj when Save then Return ResourceAlreadyExistsException")
    void testGivenAnExistingCnpj_whenSave_thenReturnResourceAlreadyExistsException() throws Exception {
        given(service.save(any(EstablishmentDto.class)))
                .willThrow(ResourceAlreadyExistsException.class);

        ResultActions response = mockMvc.perform(post(ESTABLISHMENT_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(establishmentDto)));

        response
                .andExpect(status().isConflict())
                .andDo(print());
    }

    @Test
    @DisplayName("JUnit test for Given List of Establishment when Find All then Return Establishment List")
    void testGivenListOfEstablishment_whenFindAll_thenReturnEstablishmentList() throws Exception {
        List<EstablishmentDto> establishmentList = List.of(establishmentDto);

        given(service.findAll()).willReturn(establishmentList);

        ResultActions response = mockMvc.perform(
                get(ESTABLISHMENT_BASE_URL)
                        .accept(MediaType.APPLICATION_XML));

        response
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_XML))
                .andExpect(xpath("CollectionModel/content").nodeCount(establishmentList.size()))
                .andDo(print());
    }

    @Test
    @DisplayName("JUnit test for Given EstablishmentId when FindOne then Return Establishment Object")
    void testGivenEstablishmentId_whenFindOne_thenReturnEstablishmentObject() throws Exception {
        UUID establishmentId = UUID.randomUUID();
        Establishment establishment = establishmentDto.toModel();
        establishment.setId(establishmentId);
        given(service.findOne(any(UUID.class))).willReturn(EstablishmentDto.fromModel(establishment));

        ResultActions response = mockMvc.perform(
                get(ESTABLISHMENT_BASE_URL + "/{id}", establishmentId)
                        .accept(MediaType.APPLICATION_JSON));

        response
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(establishmentId.toString())))
                .andDo(print());
    }

    @Test
    @DisplayName("JUnit test for Given non Existent EstablishmentId when FindOne then Trhows ResourceNotFoundException")
    void testGivenNonExistentEstablishmentId_whenFindOne_thenTrhowsResourceNotFoundException() throws Exception {
        UUID establishmentId = UUID.randomUUID();
        given(service.findOne(any(UUID.class)))
                .willThrow(ResourceNotFoundException.class);

        ResultActions response = mockMvc.perform(
                get(ESTABLISHMENT_BASE_URL + "/{id}", establishmentId)
                        .accept(MediaType.APPLICATION_JSON));

        response
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("JUnit test for Given Establishment Object when Update then Return Updated Establishment")
    void testGivenEstablishmentObject_whenUpdate_thenReturnUpdatedEstablishment() throws Exception {
        UUID establishmentId = UUID.randomUUID();
        String updatedName = "Establishment Test 1 Updated";
        Integer updatedNumberSpaceCar = 3;

        Establishment establishment = establishmentDto.toModel();

        establishment.setId(establishmentId);
        establishment.setName(updatedName);
        establishment.setNumberSpaceCar(updatedNumberSpaceCar);

        EstablishmentDto updatedEstablishment = EstablishmentDto.fromModel(establishment);

        given(service.update(any(UUID.class), any(EstablishmentDto.class))).willReturn(updatedEstablishment);

        ResultActions response = mockMvc.perform(
                put(ESTABLISHMENT_BASE_URL + "/{id}", establishmentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(establishmentDto)));

        response
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(establishmentId.toString())))
                .andExpect(jsonPath("$.name", is(updatedName)))
                .andExpect(jsonPath("$.numberSpaceCar", is(updatedNumberSpaceCar)))
                .andDo(print());
    }

    @Test
    @DisplayName("JUnit test for Given non Existent EstablishmentId when Update then Trhows ResourceNotFoundException")
    void testGivenNonExistentEstablishmentId_whenUpdate_thenTrhowsResourceNotFoundException() throws Exception {
        UUID establishmentId = UUID.randomUUID();
        given(service.update(any(UUID.class), any(EstablishmentDto.class)))
                .willThrow(ResourceNotFoundException.class);

        ResultActions response = mockMvc.perform(
                put(ESTABLISHMENT_BASE_URL + "/{id}", establishmentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(establishmentDto)));

        response
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("JUnit test for Given EstablishmentId when Delete then Return NoContent")
    void testGivenEstablishmentId_whenDelete_thenReturnNoContent() throws Exception {
        UUID establishmentId = UUID.randomUUID();

        willDoNothing().given(service).delete(any(UUID.class));

        ResultActions response = mockMvc.perform(
                delete(ESTABLISHMENT_BASE_URL + "/{id}", establishmentId)
                        .accept(MediaType.APPLICATION_JSON));

        response
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("JUnit test for Given non Existent EstablishmentId when Delete then Trhows ResourceNotFoundException")
    void testGivenNonExistentEstablishmentId_whenDelete_thenTrhowsResourceNotFoundException() throws Exception {
        UUID establishmentId = UUID.randomUUID();

        willThrow(ResourceNotFoundException.class).given(service).delete(any(UUID.class));

        ResultActions response = mockMvc.perform(
                delete(ESTABLISHMENT_BASE_URL + "/{id}", establishmentId)
                        .accept(MediaType.APPLICATION_JSON));

        response
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}
