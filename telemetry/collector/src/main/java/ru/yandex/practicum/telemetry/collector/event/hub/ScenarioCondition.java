package ru.yandex.practicum.telemetry.collector.event.hub;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.telemetry.collector.event.hub.enums.ConditionOperation;
import ru.yandex.practicum.telemetry.collector.event.hub.enums.ConditionType;

@Getter @Setter @ToString
public class ScenarioCondition {
    private String sensorId;
    private ConditionType type;
    private ConditionOperation operation;
    private Integer value;
}

