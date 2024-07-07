package com.ferro.mateus.redis_w_spring.controller.dtos;

import org.springframework.data.domain.Page;

public record PageResponse(Integer page, Integer size, Long totalElements, Integer totalPages) {
    public static PageResponse fromPage(Page<?> page) {
        return new PageResponse(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}
