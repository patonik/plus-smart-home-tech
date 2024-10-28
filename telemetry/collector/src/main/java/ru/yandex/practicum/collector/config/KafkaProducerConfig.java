package ru.yandex.practicum.collector.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import ru.yandex.practicum.collector.event.hub.HubEvent;
import ru.yandex.practicum.collector.event.sensor.SensorEvent;
import ru.yandex.practicum.collector.serializer.HubEventSerializer;
import ru.yandex.practicum.collector.serializer.SensorEventSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<String, SensorEvent> sensorEventProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, SensorEventSerializer.class);
        configProps.put("schema.registry.url", "http://localhost:8081");

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean(name = "sensorEventKafkaTemplate")
    public KafkaTemplate<String, SensorEvent> sensorEventKafkaTemplate() {
        return new KafkaTemplate<>(sensorEventProducerFactory());
    }

    @Bean
    public ProducerFactory<String, HubEvent> hubEventProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, HubEventSerializer.class);
        configProps.put("schema.registry.url", "http://localhost:8081");

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean(name = "hubEventKafkaTemplate")
    public KafkaTemplate<String, HubEvent> hubEventKafkaTemplate() {
        return new KafkaTemplate<>(hubEventProducerFactory());
    }
}


