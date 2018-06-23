package com.skip.the.dishes.products.controller;

import com.skip.the.dishes.products.api.CreateProductRequest;
import com.skip.the.dishes.products.api.CreateProductResponse;
import com.skip.the.dishes.products.api.ProductTO;
import com.skip.the.dishes.products.api.ProductsApi;
import com.skip.the.dishes.products.domain.Product;
import com.skip.the.dishes.products.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
public class ProductsController implements ProductsApi {

    private final ProductRepository productRepository;
    private final ModelMapper mapper;

    public ProductsController(ProductRepository productRepository, ModelMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    /**
     * Product controller
     * @title Creates a product
     */
    @Override
    public CreateProductResponse create(@RequestBody @Valid CreateProductRequest createProductRequest) {
        Product product = mapper.map(createProductRequest, Product.class);
        product.setId(UUID.randomUUID().toString());
        return mapper.map(productRepository.save(product), CreateProductResponse.class);
    }

    @Override
    public ProductTO getById(@PathVariable("productId") String productId) {
        return ProductTO.builder().name("name").description("description").build();
    }
}
