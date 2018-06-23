package com.skip.the.dishes.products.api

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.Validation
import javax.validation.Validator

class CreateProductRequestTest extends Specification {
    @Shared
    Validator validator

    def setupSpec() {
        validator = Validation.buildDefaultValidatorFactory().getValidator()
    }

    @Unroll
    def "should not create CreateProductRequest with invalid name"() {
        given:
        def request = CreateProductRequest
                .builder()
                .name(name)
                .description(description)
                .build()
        when:
        def errors = validator.validate(request)

        then:
        def violation = ++errors.iterator()
        violation.getPropertyPath().toString() == "name"

        where:
        name | description
        null | null
        ""   | null
    }
}
