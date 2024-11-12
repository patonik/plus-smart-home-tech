package ru.yandex.practicum.telemetry.serialization.common.event.hub;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.telemetry.serialization.common.event.hub.enums.HubEventType;
import ru.yandex.practicum.telemetry.serialization.common.event.hub.enums.DeviceType;

@Getter @Setter @ToString(callSuper = true)
public class DeviceAddedEvent extends HubEvent {
    private String id;
    private DeviceType type;

    @Override
    public HubEventType getType() {
        return HubEventType.DEVICE_ADDED_EVENT;
    }
}

