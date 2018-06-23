package com.skip.the.dishes.products.repository;

import com.skip.the.dishes.products.domain.Product;

public interface ProductRepository {
    Product save(Product product);
    Product findOne(String id);
}
