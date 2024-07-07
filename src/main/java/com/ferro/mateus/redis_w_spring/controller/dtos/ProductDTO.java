package com.ferro.mateus.redis_w_spring.controller.dtos;

import java.math.BigDecimal;

public record ProductDTO(String name, String description, String category,
                         BigDecimal price, Integer stock) {
}
