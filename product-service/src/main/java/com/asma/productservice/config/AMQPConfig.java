package com.asma.productservice.config;

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

    private List<QueueBinding> bindings;

    @Bean
    public Declarables queues() {
        List<Queue> queues = bindings.stream().map(binding -> new Queue(binding.getQueue())).collect(Collectors.toList());
        return new Declarables(queues.toArray(new Queue[queues.size()]));
    }

    @Bean
    public Declarables exchanges() {
        List<Exchange> exchanges = bindings.stream().map(binding -> new DirectExchange(binding.getExchange())).collect(Collectors.toList());
        return new Declarables(exchanges.toArray(new Exchange[exchanges.size()]));
    }

    @Bean
    public Declarables bindings() {
        List<Binding> bindingList = this.bindings.stream().map(binding -> new Binding(binding.getQueue(), Binding.DestinationType.QUEUE, binding.getExchange(), binding.getRoutingKey(), null)).collect(Collectors.toList());
        return new Declarables(bindingList.toArray(new Binding[bindingList.size()]));
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter converter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter);
        return rabbitTemplate;
    }

    public List<QueueBinding> getBindings() {
        return bindings;
    }

    public void setBindings(List<QueueBinding> bindings) {
        this.bindings = bindings;
    }
}
