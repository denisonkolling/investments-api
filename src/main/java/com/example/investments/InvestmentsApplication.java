package com.example.investments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class InvestmentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvestmentsApplication.class, args);
    }

}
