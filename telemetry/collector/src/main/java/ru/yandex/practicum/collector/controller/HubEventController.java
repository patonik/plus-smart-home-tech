package ru.yandex.practicum.collector.controller;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.collector.event.hub.HubEvent;

import javax.validation.Valid;

@RestController
public class HubEventController {

    private final KafkaTemplate<String, HubEvent> kafkaTemplate;
    private static final String HUB_TOPIC = "telemetry.hubs.v1";

    public HubEventController(KafkaTemplate<String, HubEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/hubs")
    public void collectHubEvent(@Valid @RequestBody HubEvent event) {
        // Send the hub event to the Kafka topic for hub data
        kafkaTemplate.send(HUB_TOPIC, event.getHubId(), event);
    }
}

