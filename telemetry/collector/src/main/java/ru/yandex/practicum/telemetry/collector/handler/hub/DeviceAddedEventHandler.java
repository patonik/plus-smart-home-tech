package ru.yandex.practicum.telemetry.collector.handler.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.DeviceAddedEventProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceTypeProto;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

@Component
public class DeviceAddedEventHandler implements HubEventHandler {

    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.DEVICE_ADDED;
    }

    @Override
    public HubEventAvro handle(HubEventProto event) {
        DeviceAddedEventProto deviceAddedEventProto = event.getDeviceAdded();
        String id = deviceAddedEventProto.getId();
        DeviceTypeProto type = deviceAddedEventProto.getType();
        DeviceTypeAvro deviceTypeAvro = DeviceTypeAvro.valueOf(type.name());
        DeviceAddedEventAvro deviceAddedEventAvro =
            DeviceAddedEventAvro.newBuilder().setId(id).setType(deviceTypeAvro).build();
        return HubEventAvro.newBuilder().setHubId(event.getHubId()).setPayload(deviceAddedEventAvro).build();
    }
}
