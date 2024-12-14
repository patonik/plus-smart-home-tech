package ru.yandex.practicum.telemetry.collector.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.grpc.telemetry.event.TemperatureSensorEvent;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventPayloadAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.TemperatureSensorEventAvro;

import java.time.Instant;

/**
 * Handler for processing temperature sensor events.
 */
@Component
public class TemperatureSensorEventHandler implements SensorEventHandler {

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.TEMPERATURE_SENSOR_EVENT;
    }

    @Override
    public SensorEventPayloadAvro handle(SensorEventProto event) {
        TemperatureSensorEvent temperatureSensor = event.getTemperatureSensorEvent();
        int temperatureC = temperatureSensor.getTemperatureC();
        int temperatureF = temperatureSensor.getTemperatureF();
        TemperatureSensorEventAvro temperatureSensorEventAvro = TemperatureSensorEventAvro.newBuilder()
            .setHubId(event.getHubId())
            .setId(event.getId())
            .setTimestamp(Instant.ofEpochSecond(event.getTimestamp().getSeconds(), event.getTimestamp().getNanos()))
            .setType(SensorEventTypeAvro.TEMPERATURE_SENSOR_EVENT)
            .setTemperatureC(temperatureC)
            .setTemperatureF(temperatureF)
            .build();
        return SensorEventPayloadAvro.newBuilder()
            .setPayload(temperatureSensorEventAvro)
            .build();
    }
}
