package ru.yandex.practicum.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "ru.yandex.practicum.interaction")
public class StoreApp {
    public static void main(String[] args) {
        SpringApplication.run(StoreApp.class, args);
    }
}
