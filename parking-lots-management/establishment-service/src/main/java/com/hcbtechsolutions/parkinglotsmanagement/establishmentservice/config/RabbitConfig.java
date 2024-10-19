package com.hcbtechsolutions.parkinglotsmanagement.establishmentservice.config;

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

    public static final String ESTABLISHMENT_QUEUE = "establishment.queue";
    public static final String TOPIC_EXCHANGE = "parking.exchange";
    public static final String ESTABLISHMENT_ROUTING_KEY = "establishment.routing.key";

    @Bean
    public Queue establishmentQueue() {
        return new Queue(ESTABLISHMENT_QUEUE, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Binding establishmentBinding(Queue establishmentQueue, TopicExchange exchange) {
        return BindingBuilder.bind(establishmentQueue).to(exchange).with(ESTABLISHMENT_ROUTING_KEY);
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
