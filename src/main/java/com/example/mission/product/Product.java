package com.example.mission.product;

import java.time.LocalDateTime;

public record Product(
        Long id,
        String name,
        int price,
        String ownerUsername,
        LocalDateTime createdAt
) {}