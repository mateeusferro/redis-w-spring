package com.ferro.mateus.redis_w_spring.controller;

import com.ferro.mateus.redis_w_spring.controller.dtos.APIResponse;
import com.ferro.mateus.redis_w_spring.controller.dtos.PageResponse;
import com.ferro.mateus.redis_w_spring.controller.dtos.ProductDTO;
import com.ferro.mateus.redis_w_spring.controller.dtos.StatsResponse;
import com.ferro.mateus.redis_w_spring.domain.entity.Product;
import com.ferro.mateus.redis_w_spring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<APIResponse<Product>> findAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<Product> products = productService.findAll(page, size);

        return new ResponseEntity<>(new APIResponse<>(
                products.getContent(),
                PageResponse.fromPage(products)
        ), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable UUID id) {
        return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<APIResponse<Product>> search(@RequestParam(required = false) String name,
                                                @RequestParam(required = false) String category,
                                                @RequestParam(defaultValue = "0") Integer page,
                                                @RequestParam(defaultValue = "10") Integer size) {
        if (name != null) {
            Page<Product> products = productService.searchByName(name, page, size);

            return new ResponseEntity<>(new APIResponse<>(
                    products.getContent(),
                    PageResponse.fromPage(products)
            ), HttpStatus.OK);
        }
        if (category != null) {
            Page<Product> products = productService.searchByCategory(category, page, size);
            return new ResponseEntity<>(new APIResponse<>(
                    products.getContent(),
                    PageResponse.fromPage(products)
            ), HttpStatus.OK);
        }
        throw new RuntimeException("Name or category not specified");
    }

    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> stats() {
        return new ResponseEntity<>(productService.stats(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> save(@RequestBody ProductDTO productDTO) {
        Product product = Product.builder()
                .name(productDTO.name())
                .description(productDTO.description())
                .category(productDTO.category())
                .price(productDTO.price())
                .stock(productDTO.stock())
                .build();
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable UUID id,
                                          @RequestBody ProductDTO productDTO) {
        Product product = Product.builder()
                .name(productDTO.name())
                .description(productDTO.description())
                .category(productDTO.category())
                .price(productDTO.price())
                .stock(productDTO.stock())
                .build();
        return new ResponseEntity<>(productService.update(id, product), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        productService.delete(id);
        return new ResponseEntity<>("Product deleted", HttpStatus.OK);
    }
}
