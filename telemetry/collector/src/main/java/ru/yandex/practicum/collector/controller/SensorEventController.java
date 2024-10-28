package ru.yandex.practicum.collector.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.collector.event.sensor.SensorEvent;

import javax.validation.Valid;

@RestController
@RequestMapping("/sensors")
public class SensorEventController {

    private final KafkaTemplate<String, SensorEvent> kafkaTemplate;
    private static final String SENSOR_TOPIC = "telemetry.sensors.v1";

    @Autowired
    public SensorEventController(@Qualifier("sensorEventKafkaTemplate") KafkaTemplate<String, SensorEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public void collectSensorEvent(@Valid @RequestBody SensorEvent event) {
        kafkaTemplate.send(SENSOR_TOPIC, event.getId(), event);
    }
}

