package ru.yandex.practicum.interaction.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID productId;

    private Boolean fragile;

    private Dimension dimension;

    private Double weight;

    private Integer quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WarehouseProduct that = (WarehouseProduct) o;
        return Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(productId);
    }

    @Override
    public String toString() {
        return "WarehouseProduct{" +
            "productId=" + productId +
            ", fragile=" + fragile +
            ", dimension=" + dimension +
            ", weight=" + weight +
            ", quantity=" + quantity +
            '}';
    }
}
