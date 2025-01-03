package com.hhplu.hhplusconcert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableRetry
@EnableCaching
@EnableScheduling
public class HhplusConcertApplication {

    public static void main(String[] args) {
        SpringApplication.run(HhplusConcertApplication.class, args);
    }

}
