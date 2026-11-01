package com.gtr3base.OrderService.service;

import com.gtr3base.OrderService.dto.OrderEvent;
import com.gtr3base.OrderService.producer.OrderProducer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderProducer orderProducer;

    public OrderService(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    public OrderEvent createOrder(String product, int quantity) {
        OrderEvent event = new OrderEvent(UUID.randomUUID(), product, quantity, LocalDateTime.now());
        orderProducer.sendOrderEvent(event);

        return event;
    }
}
