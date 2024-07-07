package com.ferro.mateus.redis_w_spring.domain.repository;

import com.ferro.mateus.redis_w_spring.controller.dtos.StatsResponse;
import com.ferro.mateus.redis_w_spring.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("SELECT p FROM Product p WHERE p.name LIKE :name%")
    Page<Product> findProductByName(String name, Pageable pageable);

    Page<Product> findAllByCategory(String category, Pageable pageable);

    @Query("SELECT AVG(p.price) as avgPrice, SUM(p.price) as totalPrice, " +
            "AVG(p.stock) as avgStock, SUM(p.stock) as totalStock, " +
            "COUNT(p.id) as countId FROM Product p")
    List<BigDecimal[]> findProductStats();
}
