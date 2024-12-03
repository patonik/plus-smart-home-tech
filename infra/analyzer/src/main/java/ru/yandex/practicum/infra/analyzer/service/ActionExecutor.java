package ru.yandex.practicum.infra.analyzer.service;

import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.grpc.telemetry.hubrouter.HubRouterControllerGrpc;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionProto;
import ru.yandex.practicum.grpc.telemetry.event.ActionTypeProto;
import ru.yandex.practicum.infra.analyzer.entity.Action;
import ru.yandex.practicum.kafka.telemetry.event.ActionTypeAvro;

import java.util.List;


@Service
public class ActionExecutor {

    private static final Logger logger = LoggerFactory.getLogger(ActionExecutor.class);

    private final HubRouterControllerGrpc.HubRouterControllerBlockingStub hubRouterClient;

    public ActionExecutor(HubRouterControllerGrpc.HubRouterControllerBlockingStub hubRouterClient) {
        this.hubRouterClient = hubRouterClient;
    }

    /**
     * Executes a list of actions by sending gRPC requests to the Hub Router service.
     * @param actions List of actions to execute.
     */
    public void executeActions(List<Action> actions) {
        for (Action action : actions) {
            try {
                DeviceActionProto deviceActionProto = toDeviceActionProto(action);
                hubRouterClient.handleDeviceAction(deviceActionProto);
                logger.info("Executed action {} for device {}", action.getType(), action.getSensorId());
            } catch (StatusRuntimeException e) {
                logger.error("Failed to execute action for device {}: {}", action.getSensorId(), e.getStatus());
            }
        }
    }

    /**
     * Converts an Action object into a DeviceActionProto for gRPC communication.
     * @param action The Action to convert.
     * @return The corresponding DeviceActionProto.
     */
    private DeviceActionProto toDeviceActionProto(Action action) {
        DeviceActionProto.Builder builder = DeviceActionProto.newBuilder()
            .setSensorId(action.getSensorId())
            .setType(convertActionType(action.getType()));

        if (action.getValue() != null) {
            builder.setValue(action.getValue());
        }

        return builder.build();
    }

    /**
     * Maps the ActionType enum from the Action model to the gRPC ActionTypeProto.
     * @param actionType The ActionType to convert.
     * @return The corresponding ActionTypeProto.
     */
    private ActionTypeProto convertActionType(ActionTypeAvro actionType) {
        return switch (actionType) {
            case ACTIVATE -> ActionTypeProto.ACTIVATE;
            case DEACTIVATE -> ActionTypeProto.DEACTIVATE;
            case INVERSE -> ActionTypeProto.INVERSE;
            case SET_VALUE -> ActionTypeProto.SET_VALUE;
        };
    }
}

