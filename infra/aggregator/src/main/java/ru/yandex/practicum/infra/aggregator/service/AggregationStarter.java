package ru.yandex.practicum.infra.aggregator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventPayloadAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import javax.annotation.PreDestroy;
import java.time.Duration;
import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class AggregationStarter {

    private final Consumer<String, SensorEventPayloadAvro> consumer;  // Kafka Consumer for sensor events
    private final Producer<String, SensorsSnapshotAvro> producer;  // Kafka Producer for snapshots
    private final AggregatorService aggregatorService;  // Service handling the event aggregation
    @Value("${kafka.topics.snapshot}")
    private String snapshotTopic;
    @Value("${kafka.topics.sensor}")
    private String sensorTopic;

    private volatile boolean running = true;  // Flag to manage shutdown

    public void start() {
        // Subscribe to the sensor events topic
        consumer.subscribe(Collections.singletonList(sensorTopic));

        try {
            while (running) {
                // Poll for sensor events with a timeout of 1 second
                ConsumerRecords<String, SensorEventPayloadAvro> records = consumer.poll(Duration.ofSeconds(1));

                // Process each record to create or update snapshots
                records.forEach(record -> {
                    try {
                        aggregatorService.updateState(record.value()).ifPresent(snapshot -> {
                            // Produce the updated snapshot to the snapshots topic
                            producer.send(
                                new ProducerRecord<>(snapshotTopic, snapshot.getHubId(), snapshot));
                        });
                    } catch (Exception e) {
                        log.error("Error processing sensor event: {}", record, e);
                    }
                });

                // Commit offsets after processing each batch of records
                consumer.commitSync();
            }
        } catch (WakeupException e) {
            // Ignored to handle shutdown request
            log.error("Processing interrupted in Aggregator", e);
        } catch (Exception e) {
            log.error("Error during event processing in Aggregator", e);
        } finally {
            shutdown();
        }
    }

    @PreDestroy
    public void shutdown() {
        log.info("Initiating shutdown of AggregationStarter");

        // Stop the polling loop
        running = false;

        try {
            // Flush and close producer to ensure all messages are sent
            producer.flush();
            producer.close();
            log.info("Producer closed successfully");

            // Close consumer after committing any outstanding offsets
            consumer.commitSync();
            consumer.close();
            log.info("Consumer closed successfully");
        } catch (Exception e) {
            log.error("Error while closing Kafka resources", e);
        }
    }
}

