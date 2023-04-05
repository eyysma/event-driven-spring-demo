package com.asma.inventoryservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableAutoConfiguration
@ConfigurationProperties(prefix = "amqp")
public class AMQPConfig {

    private List<QueueBinding> queueBindings;

    @Bean
    public Declarables queues() {
        List<Queue> queues = queueBindings.stream().map(binding -> new Queue(binding.getQueue())).collect(Collectors.toList());
        return new Declarables(queues.toArray(new Queue[queues.size()]));
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    public List<QueueBinding> getBindings() {
        return queueBindings;
    }

    public void setBindings(List<QueueBinding> bindings) {
        this.queueBindings = bindings;
    }
}
