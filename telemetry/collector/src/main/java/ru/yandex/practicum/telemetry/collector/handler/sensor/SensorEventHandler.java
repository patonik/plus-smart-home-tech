package ru.yandex.practicum.telemetry.collector.handler.sensor;

import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventPayloadAvro;

/**
 * Interface for handling specific types of sensor events.
 * Each implementation will handle one specific event type
 * identified by the PayloadCase enumeration.
 */
public interface SensorEventHandler {

    /**
     * Returns the type of event (sensor type) this handler is responsible for.
     *
     * @return the PayloadCase representing the event type.
     */
    SensorEventProto.PayloadCase getMessageType();

    /**
     * Processes the given sensor event.
     *
     * @param event the SensorEventProto object containing the event data.
     */
    SensorEventPayloadAvro handle(SensorEventProto event);
}
