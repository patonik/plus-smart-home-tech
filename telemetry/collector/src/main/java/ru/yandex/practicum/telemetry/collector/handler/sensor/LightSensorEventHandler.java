package ru.yandex.practicum.telemetry.collector.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.LightSensorEvent;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.LightSensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventPayloadAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventTypeAvro;
import java.time.Instant;

@Component
public class LightSensorEventHandler implements SensorEventHandler {

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.LIGHT_SENSOR_EVENT;
    }

    @Override
    public SensorEventPayloadAvro handle(SensorEventProto event) {
        LightSensorEvent lightSensor = event.getLightSensorEvent();
        int linkQuality = lightSensor.getLinkQuality();
        int luminosity = lightSensor.getLuminosity();
        LightSensorEventAvro lightSensorEventAvro = LightSensorEventAvro.newBuilder()
            .setHubId(event.getHubId())
            .setId(event.getId())
            .setTimestamp(Instant.ofEpochSecond(event.getTimestamp().getSeconds(), event.getTimestamp().getNanos()))
            .setType(SensorEventTypeAvro.LIGHT_SENSOR_EVENT)
            .setLinkQuality(linkQuality)
            .setLuminosity(luminosity)
            .build();
        return SensorEventPayloadAvro.newBuilder()
            .setPayload(lightSensorEventAvro)
            .build();
    }
}
