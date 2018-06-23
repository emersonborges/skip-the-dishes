package com.skip.the.dishes.products.configuration;

import io.opentracing.Tracer;
import io.opentracing.contrib.elasticsearch.TracingPreBuiltTransportClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;

@Configuration
public class ElasticsearchConfiguration {

    @Value("${elasticsearch.host}")
    private String elasticSearchHost;

    @Value("${elasticsearch.port}")
    private Integer elasticSearchPort;

    @Value("${elasticsearch.cluster.name}")
    private String elasticSearchClusterName;

    @Autowired
    private Tracer tracer;

    @Bean
    public Client client() throws Exception {
        Settings settings = Settings.builder()
                .put("cluster.name", elasticSearchClusterName)
                .build();
        TransportAddress transportAddress = new TransportAddress(
                InetAddress.getByName(elasticSearchHost), elasticSearchPort);
        return new TracingPreBuiltTransportClient(tracer, settings)
                .addTransportAddress(transportAddress);
    }

}
