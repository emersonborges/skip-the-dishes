package com.skip.the.dishes.products.controller;

import com.skip.the.dishes.products.api.ProductTO;
import com.skip.the.dishes.products.api.ProductsApi;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductsController implements ProductsApi {

    @Override
    public ProductTO getById(@PathVariable String productId) {
        return ProductTO.builder().name("name").description("description").build();
    }
}
