package ru.yandex.practicum.collector.event.hub;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.collector.event.hub.enums.ConditionOperationAvro;
import ru.yandex.practicum.collector.event.hub.enums.ConditionTypeAvro;

@Getter @Setter @ToString
public class ScenarioCondition {
    private String sensorId;
    private ConditionTypeAvro type;
    private ConditionOperationAvro operation;
    private Integer value;
}

