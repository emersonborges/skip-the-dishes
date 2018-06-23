package com.skip.the.dishes.products.repository;

class ElasticSearchRepositoryException extends RuntimeException {

    ElasticSearchRepositoryException(String message) {
        super(message);
    }

    ElasticSearchRepositoryException(String message, Throwable e) {
        super(message, e);
    }
}
