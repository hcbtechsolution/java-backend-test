package com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.config;

public class TestConfigs {

    private TestConfigs() {
        throw new IllegalStateException("Utility class");
    }

    public static final int SERVER_PORT = 9010;
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String POSTGRESQL_DOCKER_IMAGE = "postgres:16.2-alpine3.19";
}
