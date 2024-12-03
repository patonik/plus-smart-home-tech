package ru.yandex.practicum.telemetry.collector.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ClimateSensorEvent;
import ru.yandex.practicum.kafka.telemetry.event.ClimateSensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventPayloadAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventTypeAvro;

import java.time.Instant;

@Component
public class ClimateSensorEventHandler implements SensorEventHandler {

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.CLIMATE_SENSOR_EVENT;
    }

    @Override
    public SensorEventPayloadAvro handle(SensorEventProto event) {
        ClimateSensorEvent climateSensorEvent = event.getClimateSensorEvent();
        ClimateSensorEventAvro climateSensorEventAvro = ClimateSensorEventAvro.newBuilder()
            .setHubId(event.getHubId())
            .setId(event.getId())
            .setTimestamp(Instant.ofEpochSecond(event.getTimestamp().getSeconds(), event.getTimestamp().getNanos()))
            .setType(SensorEventTypeAvro.CLIMATE_SENSOR_EVENT)
            .setCo2Level(climateSensorEvent.getCo2Level())
            .setHumidity(climateSensorEvent.getHumidity())
            .setTemperatureC(climateSensorEvent.getTemperatureC())
            .build();
        return SensorEventPayloadAvro.newBuilder()
            .setPayload(climateSensorEventAvro)
            .build();
    }
}
