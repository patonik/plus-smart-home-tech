package ru.yandex.practicum.infra.analyzer.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "sensors")
@Getter
@Setter
public class Sensor {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "hub_id", nullable = false)
    private String hubId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sensor sensor = (Sensor) o;
        return Objects.equals(id, sensor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Sensor{" +
            "id='" + id + '\'' +
            ", hubId='" + hubId + '\'' +
            '}';
    }
}

