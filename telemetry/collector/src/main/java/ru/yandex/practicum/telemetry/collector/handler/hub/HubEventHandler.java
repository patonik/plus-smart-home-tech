package ru.yandex.practicum.telemetry.collector.handler.hub;

import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

public interface HubEventHandler {

    /**
     * Returns the type of event (hub type) this handler is responsible for.
     *
     * @return the PayloadCase representing the event type.
     */
    HubEventProto.PayloadCase getMessageType();

    /**
     * Processes the given hub event.
     *
     * @param event the HubEventProto object containing the event data.
     */
    HubEventAvro handle(HubEventProto event);
}
