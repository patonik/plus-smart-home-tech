package ru.yandex.practicum.interaction.dto.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeProductQuantityRequest {

    @NotBlank(message = "Product ID must not be blank.")
    private UUID productId;

    @NotNull(message = "Quantity is required.")
    @Min(value = 1, message = "Quantity must be 1 or greater.")
    private Integer quantity;
}

