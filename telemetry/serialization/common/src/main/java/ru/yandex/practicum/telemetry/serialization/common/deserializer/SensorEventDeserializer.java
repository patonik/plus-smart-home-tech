package ru.yandex.practicum.telemetry.serialization.common.deserializer;

import ru.yandex.practicum.kafka.telemetry.event.SensorEventPayloadAvro;

public class SensorEventDeserializer extends BaseAvroDeserializer<SensorEventPayloadAvro> {
    public SensorEventDeserializer() {
        super(SensorEventPayloadAvro.getClassSchema());
    }
}
