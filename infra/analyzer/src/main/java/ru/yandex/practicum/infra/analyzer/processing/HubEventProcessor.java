package ru.yandex.practicum.infra.analyzer.processing;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.infra.analyzer.entity.Action;
import ru.yandex.practicum.infra.analyzer.entity.Condition;
import ru.yandex.practicum.infra.analyzer.entity.Device;
import ru.yandex.practicum.infra.analyzer.entity.Scenario;
import ru.yandex.practicum.infra.analyzer.repo.DeviceRepository;
import ru.yandex.practicum.infra.analyzer.repo.ScenarioRepository;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HubEventProcessor implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(HubEventProcessor.class);

    private final DeviceRepository deviceRepository;
    private final ScenarioRepository scenarioRepository;
    private volatile boolean running = true;

    public HubEventProcessor(DeviceRepository deviceRepository, ScenarioRepository scenarioRepository) {
        this.deviceRepository = deviceRepository;
        this.scenarioRepository = scenarioRepository;
    }

    @Override
    public void run() {
        logger.info("HubEventProcessor started.");
        try {
            while (running) {
                // Kafka consumer will handle the actual event processing via @KafkaListener
                Thread.sleep(100); // Small delay to avoid busy waiting
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("HubEventProcessor interrupted.");
        } finally {
            logger.info("HubEventProcessor stopped.");
        }
    }

    /**
     * Listener method for processing hub events.
     * This method will handle events such as device and scenario additions or removals.
     */
    @KafkaListener(topics = "${kafka.topics.hub}", groupId = "hub-event-processor-group")
    public void processHubEvent(HubEventAvro hubEvent) {
        try {
            switch (hubEvent.getEventType()) {
                case DEVICE_ADDED:
                    addDevice(hubEvent);
                    break;
                case DEVICE_REMOVED:
                    removeDevice(hubEvent);
                    break;
                case SCENARIO_ADDED:
                    addScenario(hubEvent);
                    break;
                case SCENARIO_REMOVED:
                    removeScenario(hubEvent);
                    break;
                default:
                    logger.warn("Unknown hub event type: {}", hubEvent.getEventType());
            }
        } catch (Exception e) {
            logger.error("Error processing hub event: {}", hubEvent, e);
        }
    }

    private void addDevice(HubEventAvro event) {
        DeviceAddedEventAvro payload = (DeviceAddedEventAvro) event.getPayload();
        Device device = new Device(payload.getId(), event.getHubId(), payload.getType());
        deviceRepository.save(device);
        logger.info("Added device: {}", device.getId());
    }

    private void removeDevice(HubEventAvro event) {
        DeviceRemovedEventAvro payload = (DeviceRemovedEventAvro) event.getPayload();
        String id = payload.getId();
        deviceRepository.deleteById(id);
        logger.info("Removed device: {}", id);
    }

    private void addScenario(HubEventAvro event) {
        ScenarioAddedEventAvro payload = (ScenarioAddedEventAvro) event.getPayload();
        Scenario scenario = new Scenario();
        scenario.setName(payload.getName());
        scenario.setHubId(event.getHubId());

        List<Condition> conditions = payload.getConditions().stream().map(conditionAvro -> {
            Integer conditionValue = null;
            if (conditionAvro.getValue() instanceof Integer) {
                conditionValue = (Integer) conditionAvro.getValue();
            } else if (conditionAvro.getValue() instanceof Boolean) {
                conditionValue = (Boolean) conditionAvro.getValue() ? 1 : 0;
            }
            return new Condition(null, conditionAvro.getSensorId(), conditionAvro.getType(),
                conditionAvro.getOperation(), conditionValue, scenario);
        }).collect(Collectors.toList());

        List<Action> actions = payload.getActions().stream().map(actionAvro ->
                new Action(null, actionAvro.getSensorId(), actionAvro.getType(), actionAvro.getValue(), scenario))
            .collect(Collectors.toList());

        scenario.setConditions(conditions);
        scenario.setActions(actions);
        scenarioRepository.save(scenario);
        logger.info("Added scenario: {}", scenario.getName());
    }

    private void removeScenario(HubEventAvro event) {
        ScenarioRemovedEventAvro payload = (ScenarioRemovedEventAvro) event.getPayload();
        String name = payload.getName();
        scenarioRepository.deleteByName(name);
        logger.info("Removed scenario: {}", name);
    }

    /**
     * Stops the processing loop and shuts down the processor gracefully.
     */
    public void stop() {
        running = false;
    }
}

