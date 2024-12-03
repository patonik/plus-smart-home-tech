package ru.yandex.practicum.telemetry.collector.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.MotionSensorEvent;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.MotionSensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventPayloadAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventTypeAvro;

import java.time.Instant;


@Component
public class MotionSensorEventHandler implements SensorEventHandler {

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.MOTION_SENSOR_EVENT;
    }

    @Override
    public SensorEventPayloadAvro handle(SensorEventProto event) {
        MotionSensorEvent motionSensor = event.getMotionSensorEvent();
        boolean motionDetected = motionSensor.getMotion();
        int linkQuality = motionSensor.getLinkQuality();
        int voltage = motionSensor.getVoltage();
        MotionSensorEventAvro motionSensorEventAvro = MotionSensorEventAvro.newBuilder()
            .setHubId(event.getHubId())
            .setId(event.getId())
            .setTimestamp(Instant.ofEpochSecond(event.getTimestamp().getSeconds(), event.getTimestamp().getNanos()))
            .setType(SensorEventTypeAvro.MOTION_SENSOR_EVENT)
            .setMotion(motionDetected)
            .setLinkQuality(linkQuality)
            .setVoltage(voltage)
            .build();
        return SensorEventPayloadAvro.newBuilder()
            .setPayload(motionSensorEventAvro)
            .build();
    }
}
