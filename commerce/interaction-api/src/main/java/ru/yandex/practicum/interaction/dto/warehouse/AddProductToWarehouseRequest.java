package ru.yandex.practicum.interaction.dto.warehouse;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductToWarehouseRequest {
    @NotNull(message = "Product ID must not be null")
    private UUID productId;

    @NotNull(message = "Quantity to add is required")
    @Min(value = 1, message = "Quantity to add must be at least 1")
    private Integer quantityToAdd;
}
