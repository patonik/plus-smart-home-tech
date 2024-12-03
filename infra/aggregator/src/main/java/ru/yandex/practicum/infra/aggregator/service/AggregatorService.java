package ru.yandex.practicum.infra.aggregator.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventPayloadAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AggregatorService {

    // Map to store snapshots for each hub by hub ID
    private final Map<String, SensorsSnapshotAvro> snapshotMap = new HashMap<>();

    /**
     * Updates or creates a snapshot based on a new sensor event.
     *
     * @param event The sensor event to process.
     * @return Optional containing the updated snapshot if it was modified, or empty if no update was needed.
     */
    public Optional<SensorsSnapshotAvro> updateState(SensorEventPayloadAvro event) {
        // Get or create the snapshot for the event's hub ID
        SensorEventAvro payload = (SensorEventAvro) event.getPayload();
        SensorsSnapshotAvro snapshot = snapshotMap.computeIfAbsent(
            payload.getHubId(),
            hubId -> new SensorsSnapshotAvro(hubId, Instant.ofEpochMilli(System.currentTimeMillis()), new HashMap<>())
        );

        // Get the current state of the specific sensor within the snapshot, if present
        SensorStateAvro oldState = snapshot.getSensorsState().get(payload.getId());

        // If the sensor state is missing or outdated, update it with the event's data
        if (oldState == null || payload.getTimestamp().isAfter(oldState.getTimestamp())) {
            SensorStateAvro newState = new SensorStateAvro(payload.getTimestamp(), event.getPayload());

            // Add or update the sensor state in the snapshot
            snapshot.getSensorsState().put(payload.getId(), newState);

            // Update the snapshot timestamp to the latest event's timestamp
            snapshot.setTimestamp(payload.getTimestamp());

            // Return the modified snapshot for further processing
            return Optional.of(snapshot);
        }

        // Return empty if no update was necessary
        return Optional.empty();
    }
}


