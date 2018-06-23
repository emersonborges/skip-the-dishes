package com.skip.the.dishes.orders.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Builder
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
@Getter
public class CreateOrderRequest {
    private String productId;
}
