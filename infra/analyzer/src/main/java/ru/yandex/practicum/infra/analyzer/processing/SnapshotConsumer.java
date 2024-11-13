package ru.yandex.practicum.infra.analyzer.processing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;
import ru.yandex.practicum.telemetry.serialization.common.deserializer.SensorSnapshotDeserializer;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Slf4j
@Component
@RequiredArgsConstructor
public class SnapshotConsumer {

    private KafkaConsumer<String, SensorsSnapshotAvro> kafkaConsumer;

    @Value("${kafka.snapshot.topic}")
    private String snapshotTopic;

    @Value("${kafka.bootstrap.servers}")
    private String kafkaBootstrapServers;

    @Value("${kafka.consumer.group-id}")
    private String consumerGroupId;

    @PostConstruct
    private void initialize() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, SensorSnapshotDeserializer.class.getName());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        kafkaConsumer = new KafkaConsumer<>(properties);
        kafkaConsumer.subscribe(Collections.singletonList(snapshotTopic));
    }

    /**
     * Polls the Kafka topic for new snapshot messages.
     *
     * @return The latest SnapshotEvent from the Kafka topic.
     */
    public SensorsSnapshotAvro pollSnapshot() {
        ConsumerRecords<String, SensorsSnapshotAvro> records = kafkaConsumer.poll(Duration.ofMillis(1000));

        for (ConsumerRecord<String, SensorsSnapshotAvro> record : records) {
            log.info("Received SnapshotEvent from Kafka for hubId: {}", record.value().getHubId());
            return record.value(); // Return the first record found
        }
        return null; // Return null if no records found in this poll
    }
}

