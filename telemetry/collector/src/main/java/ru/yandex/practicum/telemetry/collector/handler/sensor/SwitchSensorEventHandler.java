package ru.yandex.practicum.telemetry.collector.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SwitchSensorEvent;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventPayloadAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.SwitchSensorEventAvro;
import java.time.Instant;

/**
 * Handler for processing switch sensor events.
 */
@Component
public class SwitchSensorEventHandler implements SensorEventHandler {

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.SWITCH_SENSOR_EVENT;
    }

    @Override
    public SensorEventPayloadAvro handle(SensorEventProto event) {
        SwitchSensorEvent switchSensor = event.getSwitchSensorEvent();
        boolean switchState = switchSensor.getState();
        SwitchSensorEventAvro switchSensorEventAvro = SwitchSensorEventAvro.newBuilder()
            .setHubId(event.getHubId())
            .setId(event.getId())
            .setTimestamp(Instant.ofEpochSecond(event.getTimestamp().getSeconds(), event.getTimestamp().getNanos()))
            .setType(SensorEventTypeAvro.SWITCH_SENSOR_EVENT)
            .setState(switchState)
            .build();
        return SensorEventPayloadAvro.newBuilder()
            .setPayload(switchSensorEventAvro)
            .build();
    }
}
