package ru.yandex.practicum.telemetry.collector.controller;

import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.grpc.CollectorControllerGrpc;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventPayloadAvro;
import ru.yandex.practicum.telemetry.collector.handler.hub.HubEventHandler;
import ru.yandex.practicum.telemetry.collector.handler.sensor.SensorEventHandler;
import ru.yandex.practicum.telemetry.collector.service.KafkaProducerService;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@GrpcService
public class EventController extends CollectorControllerGrpc.CollectorControllerImplBase {

    // Map of event handlers for different sensor types
    private final Map<SensorEventProto.PayloadCase, SensorEventHandler> sensorEventHandlers;
    private final Map<HubEventProto.PayloadCase, HubEventHandler> hubEventHandlers;
    private final KafkaProducerService kafkaProducerService;

    @Autowired
    public EventController(Set<SensorEventHandler> sensorEventHandlers,
                           Set<HubEventHandler> hubEventHandlers, KafkaProducerService kafkaProducerService) {
        this.sensorEventHandlers = sensorEventHandlers.stream()
            .collect(Collectors.toMap(
                SensorEventHandler::getMessageType,
                Function.identity()
            ));
        this.hubEventHandlers = hubEventHandlers.stream()
            .collect(Collectors.toMap(
                HubEventHandler::getMessageType,
                Function.identity()
            ));
        this.kafkaProducerService = kafkaProducerService;
    }

    /**
     * Handles incoming sensor events by routing them to the appropriate handler
     * based on the sensor type.
     *
     * @param request          The event received from a smart home sensor
     * @param responseObserver The response observer to send back the result
     */
    public void collectSensorEvent(SensorEventProto request, StreamObserver<Empty> responseObserver) {
        try {
            // Identify and retrieve the correct handler for the event type
            SensorEventHandler handler = sensorEventHandlers.get(request.getPayloadCase());
            if (handler != null) {
                SensorEventPayloadAvro sensorEvent = handler.handle(request);
                kafkaProducerService.sendSensorEvent(sensorEvent);
            } else {
                throw new IllegalArgumentException("No handler found for event type: " + request.getPayloadCase());
            }

            // Send success response back to the client
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (Exception e) {
            // Handle exceptions and send an error response
            responseObserver.onError(
                new StatusRuntimeException(
                    Status.INTERNAL.withDescription(e.getMessage()).withCause(e)
                )
            );
        }
    }

    /**
     * Handles incoming hub events by routing them to the appropriate handler
     * based on the event type.
     *
     * @param request          The event received from a smart home sensor
     * @param responseObserver The response observer to send back the result
     */
    public void collectHubEvent(HubEventProto request, StreamObserver<Empty> responseObserver) {
        try {
            // Identify and retrieve the correct handler for the event type
            HubEventHandler handler = hubEventHandlers.get(request.getPayloadCase());
            if (handler != null) {
                HubEventAvro hubEvent = handler.handle(request);
                kafkaProducerService.sendHubEvent(hubEvent);
            } else {
                throw new IllegalArgumentException("No handler found for event type: " + request.getPayloadCase());
            }

            // Send success response back to the client
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (Exception e) {
            // Handle exceptions and send an error response
            responseObserver.onError(
                new StatusRuntimeException(
                    Status.INTERNAL.withDescription(e.getMessage()).withCause(e)
                )
            );
        }
    }
}
