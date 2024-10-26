package ru.yandex.practicum.collector.event.hub;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.collector.event.hub.enums.DeviceTypeAvro;
import ru.yandex.practicum.collector.event.hub.enums.HubEventType;

@Getter @Setter @ToString(callSuper = true)
public class DeviceAddedEvent extends HubEvent {
    private String id;
    private DeviceTypeAvro type;

    @Override
    public HubEventType getType() {
        return HubEventType.DEVICE_ADDED_EVENT;
    }
}

