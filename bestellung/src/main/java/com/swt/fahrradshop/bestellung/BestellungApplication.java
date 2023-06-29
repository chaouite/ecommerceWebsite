package com.swt.fahrradshop.bestellung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication
public class BestellungApplication {
    public static void main(String[] args) {
        SpringApplication.run(BestellungApplication.class, args);
    }

}
