package com.skip.the.dishes.products.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

public interface ProductsApi {
    @RequestMapping(value = "/products/{productId}", method = GET)
    ProductTO getById(@PathVariable("productId") String productId);
}