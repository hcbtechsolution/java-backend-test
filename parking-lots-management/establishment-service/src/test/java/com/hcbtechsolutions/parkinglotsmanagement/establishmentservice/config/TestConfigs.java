package com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.config;

public class TestConfigs {

    private TestConfigs() {
        throw new IllegalStateException("Utility class");
    }

    public static final int SERVER_PORT = 9010;
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CONTENT_TYPE_XML = "application/xml";
    public static final String POSTGRESQL_DOCKER_IMAGE = "postgres:16.2-alpine3.19";
    public static final String RABBITMQ_DOCKER_IMAGE = "rabbitmq:3.13.6-management";
}
