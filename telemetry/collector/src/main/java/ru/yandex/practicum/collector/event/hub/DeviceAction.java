package ru.yandex.practicum.collector.event.hub;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.collector.event.hub.enums.ActionTypeAvro;

@Getter @Setter @ToString
public class DeviceAction {
    private String sensorId;
    private ActionTypeAvro type;
    private Integer value;
}

