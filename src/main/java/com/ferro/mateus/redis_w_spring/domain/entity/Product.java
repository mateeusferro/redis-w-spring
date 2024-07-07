package com.ferro.mateus.redis_w_spring.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    private UUID id;

    @NotNull
    @Size(max = 255, message = "Name must be max of 255 characters")
    @Column(name = "product_name")
    private String name;

    @NotNull
    @Size(max = 255, message = "Description must be max of 255 characters")
    @Column(name = "product_description")
    private String description;

    @NotNull
    @Size(max = 50, message = "Category must be max of 50 characters")
    @Column(name = "product_category")
    private String category;

    @Range(min = 0, message = "Price must be greater than 0")
    @Column(name = "product_price")
    private BigDecimal price;

    @Column(name = "product_stock")
    private Integer stock;
}
