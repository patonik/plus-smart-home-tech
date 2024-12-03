package ru.yandex.practicum.telemetry.serialization.common.event.hub;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.telemetry.serialization.common.event.hub.enums.HubEventType;

import java.util.List;

@Getter @Setter @ToString(callSuper = true)
public class ScenarioAddedEvent extends HubEvent {
    private String name;
    private List<ScenarioCondition> conditions;
    private List<DeviceAction> actions;

    @Override
    public HubEventType getType() {
        return HubEventType.SCENARIO_ADDED_EVENT;
    }
}

