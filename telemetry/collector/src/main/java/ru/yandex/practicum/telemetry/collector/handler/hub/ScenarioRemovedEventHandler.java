package ru.yandex.practicum.telemetry.collector.handler.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioRemovedEventProto;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;

@Component
public class ScenarioRemovedEventHandler implements HubEventHandler {

    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.SCENARIO_REMOVED;
    }

    @Override
    public HubEventAvro handle(HubEventProto event) {
        ScenarioRemovedEventProto scenarioRemoved = event.getScenarioRemoved();
        String name = scenarioRemoved.getName();
        ScenarioRemovedEventAvro scenarioRemovedEventAvro = ScenarioRemovedEventAvro.newBuilder().setName(name).build();
        return HubEventAvro.newBuilder().setHubId(event.getHubId()).setPayload(scenarioRemovedEventAvro).build();
    }
}
