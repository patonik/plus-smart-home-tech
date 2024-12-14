package ru.yandex.practicum.telemetry.collector.handler.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionProto;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioAddedEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioConditionProto;
import ru.yandex.practicum.kafka.telemetry.event.ActionTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.ConditionOperationAvro;
import ru.yandex.practicum.kafka.telemetry.event.ConditionTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceActionAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScenarioAddedEventHandler implements HubEventHandler {

    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.SCENARIO_ADDED;
    }

    @Override
    public HubEventAvro handle(HubEventProto event) {
        ScenarioAddedEventProto scenarioAddedEventProto = event.getScenarioAdded();
        String name = scenarioAddedEventProto.getName();
        List<ScenarioConditionProto> scenarioConditionProtos = scenarioAddedEventProto.getConditionList();
        List<DeviceActionProto> deviceActionProtos = scenarioAddedEventProto.getActionList();
        List<DeviceActionAvro> deviceActionAvros = new ArrayList<>();
        List<ScenarioConditionAvro> scenarioConditionAvros = new ArrayList<>();
        for (DeviceActionProto deviceActionProto : deviceActionProtos) {
            deviceActionAvros.add(
                DeviceActionAvro.newBuilder()
                    .setSensorId(deviceActionProto.getSensorId())
                    .setValue(deviceActionProto.getValue())
                    .setType(ActionTypeAvro.valueOf(deviceActionProto.getType().name()))
                    .build()
            );
        }
        for (ScenarioConditionProto scenarioConditionProto : scenarioConditionProtos) {
            scenarioConditionAvros.add(
                ScenarioConditionAvro.newBuilder()
                    .setSensorId(scenarioConditionProto.getSensorId())
                    .setValue(scenarioConditionProto.hasIntValue() ? scenarioConditionProto.getIntValue() :
                        scenarioConditionProto.hasBoolValue() ? scenarioConditionProto.getBoolValue() : null)
                    .setType(ConditionTypeAvro.valueOf(scenarioConditionProto.getType().name()))
                    .setOperation(ConditionOperationAvro.valueOf(scenarioConditionProto.getOperation().name()))
                    .build()
            );
        }
        ScenarioAddedEventAvro scenarioAddedEventAvro =
            ScenarioAddedEventAvro.newBuilder().setName(name).setActions(deviceActionAvros)
                .setConditions(scenarioConditionAvros).build();
        return HubEventAvro.newBuilder().setHubId(event.getHubId()).setPayload(scenarioAddedEventAvro).build();
    }
}
