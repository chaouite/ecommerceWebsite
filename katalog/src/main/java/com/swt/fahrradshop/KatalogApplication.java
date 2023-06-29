package com.swt.fahrradshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class KatalogApplication {

    public static void main(String[] args) {
        SpringApplication.run(KatalogApplication.class, args);
    }

}
