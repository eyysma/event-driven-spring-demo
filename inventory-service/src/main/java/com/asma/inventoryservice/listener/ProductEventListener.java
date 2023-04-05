package com.asma.inventoryservice.listener;

import com.asma.inventoryservice.dto.ProductCreatedDto;
import com.asma.inventoryservice.model.Inventory;
import com.asma.inventoryservice.service.InventoryService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ProductEventListener {
    @Autowired
    private InventoryService inventoryService;

    @RabbitListener(queues = "${amqp.queues.product_created}", ackMode = "MANUAL")
    public void onCreatedProductEvent(ProductCreatedDto productCreatedDto, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) Long tag) throws IOException {
        try {
            Inventory inventory = new Inventory(null, productCreatedDto.getProductId(), productCreatedDto.getQuantity());
            inventoryService.save(inventory);
            channel.basicAck(tag, false);
        } catch (RuntimeException e) {
            channel.basicNack(tag, false, false);
        }

    }
}
