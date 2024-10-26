package ru.yandex.practicum.collector.controller;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.collector.event.sensor.SensorEvent;

import javax.validation.Valid;

@RestController
public class SensorEventController {

    private final KafkaTemplate<String, SensorEvent> kafkaTemplate;
    private static final String SENSOR_TOPIC = "telemetry.sensors.v1";

    public SensorEventController(KafkaTemplate<String, SensorEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/sensors")
    public void collectSensorEvent(@Valid @RequestBody SensorEvent event) {
        // Determine the sensor type and log it
        System.out.println("Received sensor event of type: " + event.getType());

        // Send the sensor event to the Kafka topic for sensor data
        kafkaTemplate.send(SENSOR_TOPIC, event.getId(), event);
    }
}

