package com.ferro.mateus.redis_w_spring.service;

import com.ferro.mateus.redis_w_spring.controller.dtos.StatsResponse;
import com.ferro.mateus.redis_w_spring.domain.entity.Product;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ProductService {

    Page<Product> findAll(int page, int size) throws RuntimeException;

    Product findById(UUID id) throws RuntimeException;

    Page<Product> searchByName(String name, int page, int size) throws RuntimeException;

    Page<Product> searchByCategory(String category, int page, int size) throws RuntimeException;

    StatsResponse stats();

    Product save(Product product);

    Product update(UUID id, Product product) throws RuntimeException;

    void delete(UUID id);
}
