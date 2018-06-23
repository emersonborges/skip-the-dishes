package com.skip.the.dishes.products.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skip.the.dishes.products.domain.Product;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.IndexNotFoundException;
import org.springframework.stereotype.Repository;

import java.io.IOException;

import static org.elasticsearch.action.DocWriteResponse.Result.CREATED;
import static org.elasticsearch.action.DocWriteResponse.Result.UPDATED;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private static final String PRODUCTS_INDEX = "products";
    private static final String PRODUCT_TYPE = "product";
    private final Client client;
    private final ObjectMapper objectMapper;

    public ProductRepositoryImpl(Client client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    @Override
    public Product save(Product product) {
        try {
            return tryToSave(product);
        } catch (Exception ex) {
            throw new ElasticSearchRepositoryException("Unexpected error when indexing a document", ex);
        }
    }

    @Override
    public Product findOne(String id) {
        try {
            return tryToFindOne(id);
        } catch (IndexNotFoundException ex) {
            return null;
        } catch (Exception ex) {
            throw new ElasticSearchRepositoryException("Unexpected error finding by id", ex);
        }
    }

    private Product tryToSave(Product product) {
        IndexResponse response = client.prepareIndex(PRODUCTS_INDEX, PRODUCT_TYPE, getId(product))
                .setSource(toJson(product), XContentType.JSON)
                .get();

        if (response.getResult().equals(CREATED) || response.getResult().equals(UPDATED)) {
            return product;
        }

        throw new ElasticSearchRepositoryException("Unexpected response when indexing a document");
    }

    private Product tryToFindOne(String id) {
        GetResponse response = client.prepareGet(PRODUCTS_INDEX, PRODUCT_TYPE, id).get();
        if (response.isExists()) {
            return fromJson(response);
        }
        return null;
    }

    private String getId(Product product) {
        String id = product.getId();
        if (id == null) {
            throw new ElasticSearchRepositoryException("ID should not be null");
        }
        return id;
    }

    private Product fromJson(GetResponse response) {
        try {
            return objectMapper.readValue(response.getSourceAsString(), Product.class);
        } catch (IOException e) {
            throw new ElasticSearchRepositoryException("Error to read JSON");
        }
    }

    private String toJson(Product product) {
        try {
            return objectMapper.writeValueAsString(product);
        } catch (JsonProcessingException e) {
            throw new ElasticSearchRepositoryException("Error write JSON", e);
        }
    }
}
