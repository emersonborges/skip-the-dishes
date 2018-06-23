package com.skip.the.dishes.orders.integration;

import com.skip.the.dishes.products.api.ProductResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(value = "productsApi", url = "${productsApiUrl}")
public interface ProductsApi {
    @RequestMapping(value = "/products/{productId}", method = GET)
    ProductResponse getById(@PathVariable("productId") String productId);
}
