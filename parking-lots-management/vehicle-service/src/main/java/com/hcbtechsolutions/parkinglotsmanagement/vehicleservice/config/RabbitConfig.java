package com.hcbtechsolutions.parkinglotsmanagement.vehicleservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String VEHICLE_QUEUE = "vehicle.queue";
    public static final String TOPIC_EXCHANGE = "parking.exchange";
    public static final String VEHICLE_ROUTING_KEY = "vehicle.routing.key";

    @Bean
    public Queue vehicleQueue() {
        return new Queue(VEHICLE_QUEUE, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Binding vehicleBinding(Queue vehicleQueue, TopicExchange exchange) {
        return BindingBuilder.bind(vehicleQueue).to(exchange).with(VEHICLE_ROUTING_KEY);
    }
    
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
