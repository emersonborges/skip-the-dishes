package com.skip.the.dishes.orders.controller;

import com.skip.the.dishes.orders.integration.ProductsApi;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrdersController {
    private ProductsApi productsApi;

    public OrdersController(ProductsApi productsApi) {
        this.productsApi = productsApi;
    }

    @PostMapping("/orders")
    public void createOrder() {
        productsApi.getById("1");
    }
}
