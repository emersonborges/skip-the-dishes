package com.skip.the.dishes.products.controller;

import com.skip.the.dishes.products.api.CreateProductRequest;
import com.skip.the.dishes.products.api.ProductResponse;
import com.skip.the.dishes.products.api.ProductsApi;
import com.skip.the.dishes.products.domain.Product;
import com.skip.the.dishes.products.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
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
    @ResponseBody
    public ProductResponse create(@RequestBody @Valid CreateProductRequest createProductRequest) {
        Product product = mapper.map(createProductRequest, Product.class);
        product.setId(UUID.randomUUID().toString());
        return mapper.map(productRepository.save(product), ProductResponse.class);
    }

    /**
     * Product controller
     * @title Find a product by id
     */
    @Override
    @ResponseBody
    public ProductResponse getById(@PathVariable("productId") String productId) {
        return mapper.map(productRepository.findOne(productId), ProductResponse.class);
    }
}
