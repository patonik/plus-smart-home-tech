package ru.yandex.practicum.infra.analyzer.processing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.ActionTypeProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionProto;
import ru.yandex.practicum.infra.analyzer.entity.Action;
import ru.yandex.practicum.infra.analyzer.entity.Scenario;
import ru.yandex.practicum.infra.analyzer.grpc.HubRouterClient;
import ru.yandex.practicum.infra.analyzer.repo.ScenarioRepository;
import ru.yandex.practicum.infra.analyzer.service.ScenarioEvaluator;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SnapshotProcessor {
    private final ScenarioRepository scenarioRepository;
    private final HubRouterClient hubRouterClient;
    private final SnapshotConsumer snapshotConsumer;
    private final ScenarioEvaluator scenarioEvaluator;

    public void start() {
        log.info("Starting snapshot processing loop...");

        while (true) {
            SensorsSnapshotAvro snapshotEvent = snapshotConsumer.pollSnapshot();

            if (snapshotEvent != null) {
                processSnapshot(snapshotEvent);
            }
        }
    }

    private void processSnapshot(SensorsSnapshotAvro snapshotEvent) {
        String hubId = snapshotEvent.getHubId();
        log.info("Processing snapshot for hubId: {}", hubId);

        // Retrieve all scenarios associated with the hub
        List<Scenario> scenarios = scenarioRepository.findByHubId(hubId);

        // Evaluate scenarios and execute actions if conditions are met
        List<Action> actions = scenarioEvaluator.evaluate(scenarios, snapshotEvent);
        actions.forEach(action -> executeAction(action, hubId));
    }

    private void executeAction(Action action, String hubId) {
        log.info("Executing action {} on device {}", action.getType(), action.getSensorId());
        hubRouterClient.sendDeviceAction(DeviceActionProto
            .newBuilder()
            .setSensorId(action.getSensorId())
            .setType(ActionTypeProto.valueOf(action.getType().name()))
            .setValue(action.getValue())
            .build());
    }
}
