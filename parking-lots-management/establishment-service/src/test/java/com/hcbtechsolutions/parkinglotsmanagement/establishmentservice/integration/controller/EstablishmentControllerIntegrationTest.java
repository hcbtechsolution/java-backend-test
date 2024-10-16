package com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.integration.controller;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.config.TestConfigs;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.contextinitializer.AbstractApplicationContextInitializer;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.dto.AddressDto;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.dto.EstablishmentDto;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.dto.PhoneDto;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.enums.StateEnum;
import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.model.Establishment;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class EstablishmentControllerIntegrationTest extends AbstractApplicationContextInitializer {
    
    private static RequestSpecification specification;
    private static ObjectMapper mapper;
    private static XmlMapper xmlMapper;
    private static EstablishmentDto establishmentDto;

    private static final String ESTABLISHMENT_BASE_URL = "/establishments";

    @BeforeAll
    public static void setUp() {
        mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        xmlMapper = new XmlMapper();
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        specification = new RequestSpecBuilder()
            .setBasePath(ESTABLISHMENT_BASE_URL)
            .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
            .build();

        establishmentDto = new EstablishmentDto(
            "87.782.952/0001-92", 
            "Establishment Test 1", 
            new AddressDto(
                "Rua Oiapoque", 
                "60", 
                "casa", 
                "Aleixo",
                "Manaus",
                StateEnum.AM,
                "69060-170"),
            new PhoneDto("92", "98199-5567"),
            6,
            6);
    }

    @Test
    @Order(1)
    @DisplayName("JUnit integration for Given Establishment Object when Create One Establishment "+
                    "should Return Created Establishment")
    void integrationTestGivenEstablishmentObject_whenCreateOneEstablishment_shouldReturnCreatedEstablishment() 
            throws JsonProcessingException {
        var content = given()
                        .spec(specification)
                            .contentType(TestConfigs.CONTENT_TYPE_JSON)
                            .accept(TestConfigs.CONTENT_TYPE_JSON)
                            .body(establishmentDto)
                    .when()
                        .post()
                    .then()
                        .statusCode(HttpStatus.CREATED.value())
                            .extract()
                                .body()
                                    .asString();
            

        EstablishmentDto createdEstablishment = mapper.readValue(content, EstablishmentDto.class);
        establishmentDto = createdEstablishment;

        assertNotNull(createdEstablishment);
        assertNotNull(createdEstablishment.id());
        assertNotNull(createdEstablishment.address());
        assertNotNull(createdEstablishment.address().id());
        assertNotNull(createdEstablishment.phone());
        assertNotNull(createdEstablishment.phone().id());
        assertEquals(establishmentDto.name(), createdEstablishment.name());
        assertEquals(establishmentDto.address().city(), createdEstablishment.address().city());
        assertEquals(establishmentDto.phone().number(), createdEstablishment.phone().number());
    }

    @Test
    @Order(2)
    @DisplayName("JUnit integration for Given Establishment Id when Get One Establishment "+ 
                    "should Return Found Establishment")
    void integrationTestGivenEstablishmentId_whenGetOneEstablishment_ShouldReturnFoundEstablishment() 
            throws JsonProcessingException {
        UUID establishmentId = establishmentDto.id();
        var content = given()
                        .spec(specification)
                            .accept(TestConfigs.CONTENT_TYPE_XML)
                            .pathParam("id", establishmentId)
                    .when()
                        .get("{id}")
                    .then()
                        .statusCode(HttpStatus.OK.value())
                            .extract()
                                .body()
                                    .asString();

        EstablishmentDto foundEstablishment = xmlMapper.readValue(content, EstablishmentDto.class);

        assertNotNull(foundEstablishment);
        assertEquals(establishmentId, foundEstablishment.id());
    }

    @Test
    @Order(3)
    @DisplayName("JUnit integration for Given Establishment Object when Update One Establishment should ReturnUpdatedEstablishment") 
    void integrationTestGivenEstablishmentObject_whenUpdateOneEstablishment_ShouldReturnUpdatedEstablishment() 
            throws JsonProcessingException {
        UUID establishmentId = establishmentDto.id();
        String newName = "Establishment Test 1 Updated";

        Establishment establishment = establishmentDto.toModel();
        establishment.setName(newName);
        establishmentDto = EstablishmentDto.fromModel(establishment);

        var content = given()
                        .spec(specification)
                            .contentType(TestConfigs.CONTENT_TYPE_JSON)
                            .accept(TestConfigs.CONTENT_TYPE_JSON)
                            .pathParam("id", establishmentId)
                            .body(establishmentDto)
                    .when()
                        .put("{id}")
                    .then()
                        .statusCode(HttpStatus.OK.value())
                            .extract()
                                .body()
                                    .asString();

        EstablishmentDto updatedEstablishment = mapper.readValue(content, EstablishmentDto.class);
        establishmentDto = updatedEstablishment;
        
        assertNotNull(updatedEstablishment);
        assertEquals(establishmentId, updatedEstablishment.id());
        assertEquals(newName, updatedEstablishment.name());
    }

    @Test
    @Order(4)
    @DisplayName("JUnit integration for Given List of Establishment when Get All Establishment "+ 
                    "should Return A Establishment List")
    void integrationTestGivenListofEstablishment_whenGetAllEstablishment_ShouldReturnAEstablishmentList() 
            throws JsonProcessingException {
        EstablishmentDto anotherEstablishment = new EstablishmentDto(
            "28.248.083/0001-51", 
            "Establishment Test 2", 
            new AddressDto(
                "Rua Atagamita", 
                "180", 
                "Ap 02", 
                "Aleixo",
                "Manaus",
                StateEnum.AM,
                "69060-050"),
            new PhoneDto("92", "98498-5948"),
            3,
            3);
        
        given()
            .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .accept(TestConfigs.CONTENT_TYPE_JSON)
                .body(anotherEstablishment)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value())
                .extract()
                    .body()
                        .asString();

        List<EstablishmentDto> establishmentDtoList = Arrays.asList(establishmentDto, anotherEstablishment);

        var content = given()
                        .spec(specification)
                            .accept(TestConfigs.CONTENT_TYPE_JSON)
                    .when()
                        .get()
                    .then()
                        .statusCode(HttpStatus.OK.value())
                            .extract()
                                .body()
                                    .asString();

        EstablishmentDto[] arrayOfEstablishment = mapper.readValue(content, EstablishmentDto[].class);
        List<EstablishmentDto> establishments = Arrays.asList(arrayOfEstablishment);

        assertNotNull(establishments);
        assertEquals(2, establishments.size());

        IntStream.range(0, establishments.size()).forEach(i -> {
            EstablishmentDto expected = establishmentDtoList.get(i);
            EstablishmentDto actual = establishments.get(i);

            assertNotNull(actual.id());
            assertNotNull(actual.address());
            assertNotNull(actual.address().id());
            assertNotNull(actual.phone());
            assertNotNull(actual.phone().id());

            assertEquals(expected.name(), actual.name());
            assertEquals(expected.address().city(), actual.address().city());
            assertEquals(expected.phone().number(), actual.phone().number());
        });
    }

    @Test
    @Order(5)
    @DisplayName("JUnit integration for Given EstablishmentId when DeleteOneEstablishment should Return NoContent ")
    void integrationTestGivenEstablishmentId_whenDeleteOneEstablishment_ShouldReturnNoContent() {
        UUID establishmentId = establishmentDto.id();
        given()
            .spec(specification)
                .pathParam("id", establishmentId)
        .when()
            .delete("{id}")
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
