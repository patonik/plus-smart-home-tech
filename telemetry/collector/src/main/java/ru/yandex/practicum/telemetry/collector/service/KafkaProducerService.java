package ru.yandex.practicum.telemetry.collector.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventPayloadAvro;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, SensorEventPayloadAvro> sensorKafkaTemplate;
    private final KafkaTemplate<String, HubEventAvro> hubKafkaTemplate;

    @Value("${kafka.topics.sensor}")
    private String sensorTopic;

    @Value("${kafka.topics.hub}")
    private String hubTopic;

    @Autowired
    public KafkaProducerService(
        @Qualifier("sensorEventKafkaTemplate") KafkaTemplate<String, SensorEventPayloadAvro> sensorKafkaTemplate,
        @Qualifier("hubEventKafkaTemplate") KafkaTemplate<String, HubEventAvro> hubKafkaTemplate) {
        this.sensorKafkaTemplate = sensorKafkaTemplate;
        this.hubKafkaTemplate = hubKafkaTemplate;
    }

    public void sendSensorEvent(SensorEventPayloadAvro event) {
        try {
            SensorEventAvro sensorEventAvro = (SensorEventAvro) event.getPayload();
            sensorKafkaTemplate.send(sensorTopic, sensorEventAvro.getId(), event);
        } catch (KafkaException e) {
            System.err.println("Failed to send sensor event to Kafka: " + e.getMessage());
        }
    }

    public void sendHubEvent(HubEventAvro event) {
        try {
            hubKafkaTemplate.send(hubTopic, event.getHubId(), event);
        } catch (KafkaException e) {
            System.err.println("Failed to send hub event to Kafka: " + e.getMessage());
        }
    }
}
