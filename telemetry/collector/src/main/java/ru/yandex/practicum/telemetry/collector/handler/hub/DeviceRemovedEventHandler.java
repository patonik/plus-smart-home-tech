package ru.yandex.practicum.telemetry.collector.handler.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.DeviceRemovedEventProto;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

@Component
public class DeviceRemovedEventHandler implements HubEventHandler {

    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.DEVICE_REMOVED;
    }

    @Override
    public HubEventAvro handle(HubEventProto event) {
        DeviceRemovedEventProto deviceRemoved = event.getDeviceRemoved();
        String id = deviceRemoved.getId();
        return HubEventAvro.newBuilder().setHubId(event.getHubId())
            .setPayload(DeviceRemovedEventAvro.newBuilder().setId(id).build()).build();
    }
}
