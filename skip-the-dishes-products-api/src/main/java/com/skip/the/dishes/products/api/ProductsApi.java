package com.skip.the.dishes.products.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

public interface ProductsApi {

    @PostMapping(value = "/products", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    CreateProductResponse create(@RequestBody CreateProductRequest createProductRequest);

    @RequestMapping(value = "/products/{productId}", method = GET)
    ProductTO getById(@PathVariable("productId") String productId);
}
