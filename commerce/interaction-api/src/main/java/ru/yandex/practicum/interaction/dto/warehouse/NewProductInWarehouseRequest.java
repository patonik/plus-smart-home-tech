package ru.yandex.practicum.interaction.dto.warehouse;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewProductInWarehouseRequest {
    @NotNull(message = "Product ID must not be null")
    private UUID productId;

    private Boolean fragile;

    @NotNull(message = "dimensions are required")
    private DimensionDto dimension;

    @NotNull(message = "weight is required")
    @Min(value = 1, message = "weight must be at least 1")
    private Double weight;
}
