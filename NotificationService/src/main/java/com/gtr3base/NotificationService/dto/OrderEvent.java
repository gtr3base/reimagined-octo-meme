package com.gtr3base.NotificationService.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderEvent(
        UUID orderId,
        String product,
        int quantity,
        LocalDateTime createdAt
) {}
