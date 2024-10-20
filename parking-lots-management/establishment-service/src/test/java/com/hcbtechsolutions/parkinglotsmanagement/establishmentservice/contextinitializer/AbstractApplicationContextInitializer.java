package com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.contextinitializer;

import java.util.Map;
import java.util.stream.Stream;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.lifecycle.Startables;

import com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.config.TestConfigs;

@ContextConfiguration(initializers = AbstractApplicationContextInitializer.Initializer.class)   
public class AbstractApplicationContextInitializer {

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        static PostgreSQLContainer<?> postgresql = new PostgreSQLContainer<>(TestConfigs.POSTGRESQL_DOCKER_IMAGE);
        static RabbitMQContainer rabbitmq = new RabbitMQContainer(TestConfigs.RABBITMQ_DOCKER_IMAGE);

        private static void startContainers() {
            Startables.deepStart(Stream.of(postgresql, rabbitmq)).join();
        }
        
        private static Map<String, String> createConnectionConfiguration(){
            return Map.of(
                "spring.datasource.url", postgresql.getJdbcUrl(),
                "spring.datasource.username", postgresql.getUsername(),
                "spring.datasource.password", postgresql.getPassword(),
                "spring.rabbitmq.host", rabbitmq.getHost(),
                "spring.rabbitmq.port", rabbitmq.getAmqpPort().toString(),
                "spring.rabbitmq.username", rabbitmq.getAdminUsername(),
                "spring.rabbitmq.password", rabbitmq.getAdminPassword());
        }
        
        @Override
        @SuppressWarnings({ "rawtypes", "unchecked" })
            public void initialize(ConfigurableApplicationContext applicationContext) {
            startContainers();
            
            ConfigurableEnvironment environment = applicationContext.getEnvironment();
            
            MapPropertySource testcontainers = 
                    new MapPropertySource("testcontainers", (Map) createConnectionConfiguration());
            
            environment.getPropertySources().addFirst(testcontainers);
        }
    }
}
