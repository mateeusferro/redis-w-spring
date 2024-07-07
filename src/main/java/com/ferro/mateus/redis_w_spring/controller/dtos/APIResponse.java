package com.ferro.mateus.redis_w_spring.controller.dtos;

import java.util.List;

public record APIResponse<T>(List<T> data, PageResponse paging) {
}
