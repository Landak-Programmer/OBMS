package com.obms.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.util.List;


@SpringBootApplication
@EnableEurekaClient
public class APIGatewayApplication {

    @Autowired
    private DiscoveryClient discoveryClient;

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(APIGatewayApplication.class)
                .run(args);
    }
}
