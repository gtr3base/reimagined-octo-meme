package com.gtr3base.OrderService.producer;

import com.gtr3base.OrderService.dto.OrderEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class OrderProducer {

    public static final String TOPIC = "order-created";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public OrderProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendOrderEvent(OrderEvent orderEvent) {
        String msg = objectMapper.writeValueAsString(orderEvent);

        kafkaTemplate.send(
                TOPIC,
                orderEvent.orderId().toString(),
                msg
        ).whenComplete((result, ex) -> {
            if(ex != null) {
                System.err.println(
                        "Failed to send order event: " + ex.getMessage()
                );
            }else{
                System.out.println(
                        "Order event sent successfully. Offset: "
                            + result.getRecordMetadata().offset()
                );
            }
        });
    }
}
