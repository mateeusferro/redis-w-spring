package com.ferro.mateus.redis_w_spring.service.impl;

import com.ferro.mateus.redis_w_spring.controller.dtos.StatsResponse;
import com.ferro.mateus.redis_w_spring.domain.entity.Product;
import com.ferro.mateus.redis_w_spring.domain.repository.ProductRepository;
import com.ferro.mateus.redis_w_spring.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public Page<Product> findAll(int page, int size) {
        if (page < 0 || size < 0) {
            throw new RuntimeException("Page or page size must be greater than 0");
        }
        if (size > 50) {
            throw new RuntimeException("Page size must be less than 50");
        }

        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    @Override
    @Cacheable(value = "product", key = "#id")
    public Product findById(UUID id) {
        return productRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product not found"));
    }

    @Override
    public Page<Product> searchByName(String name, int page, int size) {
        return productRepository.findProductByName(name, PageRequest.of(page, size));
    }

    @Override
    public Page<Product> searchByCategory(String category, int page, int size) {
        return productRepository.findAllByCategory(category, PageRequest.of(page, size));
    }

    @Override
    @Cacheable(value = "product", key = "'productStats'")
    public StatsResponse stats() {
        List<BigDecimal[]> stats = productRepository.findProductStats();
        StatsResponse statsResponse = null;
        for (BigDecimal[] stat : stats) {
            statsResponse = new StatsResponse(stat[0], stat[1], stat[2], stat[3], stat[4]);
        }
        return statsResponse;
    }

    @Override
    @CacheEvict(value = "product", key = "'productStats'")
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "product", key = "#id"),
            @CacheEvict(value = "product", key = "'productStats'")
    })
    public Product update(UUID id, Product product) {
       productRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product not found"));
        product.setId(id);
        return productRepository.save(product);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "product", key = "#id"),
            @CacheEvict(value = "product", key = "'productStats'")
    })
    public void delete(UUID id) {
        productRepository.deleteById(id);
    }
}
