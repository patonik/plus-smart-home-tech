package ru.practicum.yandex.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "ru.yandex.practicum.interaction")
public class PaymeApp {
    public static void main(String[] args) {
        SpringApplication.run(PaymeApp.class, args);
    }
}
