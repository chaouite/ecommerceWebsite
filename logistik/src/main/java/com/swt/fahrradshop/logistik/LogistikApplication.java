package com.swt.fahrradshop.logistik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.google.gson.*;

@EnableDiscoveryClient
@SpringBootApplication
public class LogistikApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogistikApplication.class, args);
    }

    public static String JSONStringTOString(String JSONString,String key){
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = jsonParser.parse(JSONString).getAsJsonObject();
		return jsonObject.get(key).getAsString();
	}
}
