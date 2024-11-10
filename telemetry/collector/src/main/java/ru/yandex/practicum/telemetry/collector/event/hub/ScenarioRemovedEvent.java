package ru.yandex.practicum.telemetry.collector.event.hub;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.telemetry.collector.event.hub.enums.HubEventType;

@Getter @Setter @ToString(callSuper = true)
public class ScenarioRemovedEvent extends HubEvent {
    private String name;

    @Override
    public HubEventType getType() {
        return HubEventType.SCENARIO_REMOVED_EVENT;
    }
}

