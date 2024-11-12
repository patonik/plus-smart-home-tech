package ru.yandex.practicum.infra.aggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import ru.yandex.practicum.infra.aggregator.service.AggregationStarter;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AggregatorApp {

    public static void main(String[] args) {
        // Start the Spring Boot application and get the configured application context
        ConfigurableApplicationContext context = SpringApplication.run(AggregatorApp.class, args);

        // Retrieve the AggregationStarter bean and start the main processing loop
        AggregationStarter aggregator = context.getBean(AggregationStarter.class);
        aggregator.start();
    }
}

