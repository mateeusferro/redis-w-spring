package com.ferro.mateus.redis_w_spring.controller.dtos;

import java.math.BigDecimal;

public record StatsResponse(BigDecimal avgPrice, BigDecimal totalPrice,
                            BigDecimal avgStock, BigDecimal totalStock, BigDecimal countId) {
}
