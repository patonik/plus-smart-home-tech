package ru.yandex.practicum.interaction.dto.order;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.interaction.enums.OrderState;

import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    @NotNull
    private UUID orderId;
    private UUID shoppingCartId;
    @NotNull
    @NotEmpty
    private Map<UUID, @Min(value = 1, message = "Quantity must be at least 1") Integer> products;
    private UUID paymentId;
    private UUID deliveryId;
    private OrderState state;
    @DecimalMin(value = "0.0", inclusive = true, message = "deliveryWeight must be non-negative")
    private Double deliveryWeight;
    @DecimalMin(value = "0.0", inclusive = true, message = "deliveryVolume must be non-negative")
    private Double deliveryVolume;
    private Boolean fragile;
    @DecimalMin(value = "0.0", inclusive = true, message = "totalPrice must be non-negative")
    private Double totalPrice;
    @DecimalMin(value = "0.0", inclusive = true, message = "deliveryPrice must be non-negative")
    private Double deliveryPrice;
    @DecimalMin(value = "0.0", inclusive = true, message = "productPrice must be non-negative")
    private Double productPrice;
}


