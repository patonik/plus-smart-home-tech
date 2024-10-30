package ru.yandex.practicum.collector.event.hub;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.collector.event.hub.enums.HubEventType;

@Getter @Setter @ToString(callSuper = true)
public class DeviceRemovedEvent extends HubEvent {
    private String id;

    @Override
    public HubEventType getType() {
        return HubEventType.DEVICE_REMOVED_EVENT;
    }
}

