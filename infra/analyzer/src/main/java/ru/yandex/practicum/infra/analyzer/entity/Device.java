package ru.yandex.practicum.infra.analyzer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.yandex.practicum.kafka.telemetry.event.DeviceTypeAvro;

@Entity
@Table(name = "devices")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Device {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "hub_id", nullable = false)
    private String hubId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private DeviceTypeAvro deviceType;

}

