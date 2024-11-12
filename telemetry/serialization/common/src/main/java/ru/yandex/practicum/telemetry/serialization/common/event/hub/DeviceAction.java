package ru.yandex.practicum.telemetry.serialization.common.event.hub;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.telemetry.serialization.common.event.hub.enums.ActionType;

@Getter @Setter @ToString
public class DeviceAction {
    private String sensorId;
    private ActionType type;
    private Integer value;
}

