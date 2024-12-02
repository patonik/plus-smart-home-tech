package ru.yandex.practicum.interaction.domain;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Dimension {

    private Double width;

    private Double height;

    private Double depth;

    public double getVolume() {
        return width * height * depth;
    }
}
