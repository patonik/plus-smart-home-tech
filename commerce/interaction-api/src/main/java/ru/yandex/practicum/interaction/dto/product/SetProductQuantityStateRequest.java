package ru.yandex.practicum.interaction.dto.product;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.interaction.enums.QuantityState;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetProductQuantityStateRequest {

    @NotNull(message = "Product ID must not be null")
    private UUID productId;

    @NotNull(message = "Quantity state must not be null")
    private QuantityState quantityState;
}

