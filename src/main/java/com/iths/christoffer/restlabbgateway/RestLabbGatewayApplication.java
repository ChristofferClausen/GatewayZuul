package com.iths.christoffer.restlabbgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class RestLabbGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestLabbGatewayApplication.class, args);
    }

}
