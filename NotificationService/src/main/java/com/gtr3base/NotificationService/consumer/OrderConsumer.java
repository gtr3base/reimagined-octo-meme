package com.gtr3base.NotificationService.consumer;

import com.gtr3base.NotificationService.dto.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class OrderConsumer {

    private final ObjectMapper mapper;

    public OrderConsumer(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @KafkaListener(
            topics = "order-created",
            groupId = "notification-group"
    )
    public void consume(String msg){
        try {
            OrderEvent event = mapper.readValue(
                    msg,
                    OrderEvent.class
            );

            System.out.println("========== NEW ORDER ==========");
            System.out.println("Order ID: " + event.orderId());
            System.out.println("Product: " + event.product());
            System.out.println("Quantity: " + event.quantity());
            System.out.println("Created at: " + event.createdAt());
            System.out.println("===============================");
        }
        catch (Exception e){
            System.err.println("Failed to process order event: " + e.getMessage());
            throw new IllegalStateException("Order event processing failed", e);
        }
    }
}
