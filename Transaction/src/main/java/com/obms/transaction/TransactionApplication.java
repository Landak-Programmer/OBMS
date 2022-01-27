package com.obms.transaction;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class TransactionApplication {

    public static void main(String[] args) {

        new SpringApplicationBuilder()
                .sources(TransactionApplication.class)
                .run(args);
    }
}
