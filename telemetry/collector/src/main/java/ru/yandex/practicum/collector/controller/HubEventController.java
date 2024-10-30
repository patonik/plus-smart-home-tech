package ru.yandex.practicum.collector.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.collector.event.hub.HubEvent;

import javax.validation.Valid;

@RestController
@RequestMapping("/hubs")
public class HubEventController {

    private final KafkaTemplate<String, HubEvent> kafkaTemplate;
    private static final String HUB_TOPIC = "telemetry.hubs.v1";
    @Autowired
    public HubEventController(@Qualifier("hubEventKafkaTemplate") KafkaTemplate<String, HubEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public void collectHubEvent(@Valid @RequestBody HubEvent event) {
        kafkaTemplate.send(HUB_TOPIC, event.getHubId(), event);
    }
}

