package ru.yandex.practicum.infra.analyzer.grpc;

import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionProto;
import ru.yandex.practicum.grpc.telemetry.hubrouter.HubRouterControllerGrpc.HubRouterControllerBlockingStub;

@Slf4j
@Service
@RequiredArgsConstructor
public class HubRouterClient {

    // Inject the gRPC client stub for communication with the hub router service.
    @GrpcClient("hub-router")
    private final HubRouterControllerBlockingStub hubRouterClient;

    /**
     * Sends a device action request to the Hub Router service for execution.
     *
     * @param request the DeviceActionRequest to be executed.
     */
    public void sendDeviceAction(DeviceActionProto request) {
        String sensorId = request.getSensorId();
        try {
            log.info("Sending device action for deviceId: {} with actionType: {}", sensorId,
                request.getType());

            // Send the device action request to the hub router
            hubRouterClient.handleDeviceAction(request);

            log.info("Action successfully sent for deviceId: {}", sensorId);
        } catch (StatusRuntimeException e) {
            log.error("Failed to send device action for deviceId: {}. Error: {}", sensorId, e.getStatus());
        }
    }
}

