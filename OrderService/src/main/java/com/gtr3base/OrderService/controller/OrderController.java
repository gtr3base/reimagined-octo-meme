package com.gtr3base.OrderService.controller;

import com.gtr3base.OrderService.dto.OrderEvent;
import com.gtr3base.OrderService.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public OrderEvent createOrder(
            @RequestParam String product,
            @RequestParam int quantity
    ) {
        return orderService.createOrder(product, quantity);
    }
}
