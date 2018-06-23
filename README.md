[![Build Status](https://travis-ci.org/emersonborges/skip-the-dishes.svg?branch=master)](https://travis-ci.org/emersonborges/skip-the-dishes)
[![codecov](https://codecov.io/gh/emersonborges/skip-the-dishes/branch/master/graph/badge.svg)](https://codecov.io/gh/emersonborges/skip-the-dishes)
# skip-the-dishes
Skip the Dishes test

## Architecture 
![](skip-the-dishes-test.png)

## Distributed Tracing with Open Tracing and Jaeger

* Open Tracing: It's a standard API cross language and vendor free (http://opentracing.io/)     
* Jaeger: It's a Open Tracing implementation (http://www.jaegertracing.io/)
* Examples
    * Traces all communication between microservices (Sync and Async)
    ![Alt text](jaeger.png)
    * Traces and identify where the problem is in a transaction view
    ![Alt text](jaeger-error.png)

## Technologies used
* Java 8
* Spring Boot
* Spock Test Framework
* Postgres
* Elasticsearch
* Kafka
* Opentracing
* Jaeger