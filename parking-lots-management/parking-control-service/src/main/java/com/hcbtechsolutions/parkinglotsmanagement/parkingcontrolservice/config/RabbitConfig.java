package com.hcbtechsolutions.parkinglotsmanagement.parkingcontrolservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String VEHICLE_QUEUE = "vehicle.queue";
    public static final String ESTABELECIMENTO_QUEUE = "establishment.queue";

    @Bean
    public Queue vehicleQueue() {
        return new Queue(VEHICLE_QUEUE, true);
    }

    @Bean
    public Queue establishmentQueue() {
        return new Queue(ESTABELECIMENTO_QUEUE, true);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
