package ru.yandex.practicum.collector.serializer;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import ru.yandex.practicum.kafka.telemetry.event.HubEvent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HubEventSerializer implements Serializer<HubEvent> {


    @Override
    public byte[] serialize(String topic, HubEvent data) {
        if (data == null) {
            return null;
        }

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Schema schema = HubEvent.getClassSchema();
            BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
            GenericDatumWriter<HubEvent> writer = new GenericDatumWriter<>(schema);
            writer.write(data, encoder);
            encoder.flush();
            return out.toByteArray();
        } catch (IOException e) {
            throw new SerializationException("Error serializing SensorEvent", e);
        }
    }

    @Override
    public void close() {
    }
}

