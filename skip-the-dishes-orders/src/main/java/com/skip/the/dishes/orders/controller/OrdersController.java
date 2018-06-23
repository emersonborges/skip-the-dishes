package com.skip.the.dishes.orders.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skip.the.dishes.orders.integration.ProductsApi;
import com.skip.the.dishes.products.api.ProductResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class OrdersController {
    private ProductsApi productsApi;
    private KafkaTemplate<String, String> kafkaTemplate;
    private String topic;
    private ObjectMapper objectMapper;

    public OrdersController(ProductsApi productsApi,
                            KafkaTemplate<String, String> kafkaTemplate,
                            @Value("${kafka.topic}") String topic, ObjectMapper objectMapper) {
        this.productsApi = productsApi;
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
        this.objectMapper = objectMapper;
    }

    @PostMapping(value = "/orders", consumes = APPLICATION_JSON_VALUE)
    public void createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        //Save on relational database
        ProductResponse product = productsApi.getById(createOrderRequest.getProductId());
        OrderCreated event = OrderCreated.builder().productName(product.getName()).build();
        kafkaTemplate.send(topic, toJson(event));
    }

    private String toJson(OrderCreated event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error to parse event");
        }
    }
}
