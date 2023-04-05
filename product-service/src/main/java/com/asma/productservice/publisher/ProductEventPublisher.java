package com.asma.productservice.publisher;

import com.asma.productservice.dto.ProductCreatedDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProductEventPublisher {
    @Value("${amqp.exchanges.product_events}")
    private String product_event_exchange;

    @Value("${amqp.routingKey.product_created_event}")
    private String product_created_event;

    private final RabbitTemplate rabbitTemplate;

    public ProductEventPublisher(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishProductEvent(ProductCreatedDto productCreated){
        rabbitTemplate.convertAndSend(product_event_exchange, product_created_event, productCreated);
    }


}
