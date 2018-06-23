package com.skip.the.dishes.products.api;

import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

public interface ProductsApi {

    @ResponseStatus(CREATED)
    @PostMapping(value = "/products", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    ProductResponse create(@RequestBody CreateProductRequest createProductRequest);

    @RequestMapping(value = "/products/{productId}", method = GET, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    ProductResponse getById(@PathVariable("productId") String productId);
}
