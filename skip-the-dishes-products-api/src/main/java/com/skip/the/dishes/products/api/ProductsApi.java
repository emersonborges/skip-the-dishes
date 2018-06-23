package com.skip.the.dishes.products.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface ProductsApi {
    @GetMapping("/products/{productId}")
    ProductTO getById(@PathVariable String productId);
}
