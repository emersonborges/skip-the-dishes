package com.skip.the.dishes.orders.controller;

import com.skip.the.dishes.orders.integration.ProductsApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrdersController {
    private ProductsApi productsApi;
    private KafkaTemplate<String, String> kafkaTemplate;
    private String topic;

    public OrdersController(ProductsApi productsApi,
                            KafkaTemplate<String, String> kafkaTemplate,
                            @Value("${kafka.topic}") String topic) {
        this.productsApi = productsApi;
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @PostMapping("/orders")
    public void createOrder() {
        productsApi.getById("1");
        kafkaTemplate.send(topic, "OrderCreated");
    }
}
