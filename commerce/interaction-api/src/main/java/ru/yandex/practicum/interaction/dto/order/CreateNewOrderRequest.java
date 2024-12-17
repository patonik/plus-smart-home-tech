package ru.yandex.practicum.interaction.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.interaction.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.interaction.dto.warehouse.AddressDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNewOrderRequest {
    @NotNull
    private ShoppingCartDto shoppingCart;
    @NotNull
    private AddressDto deliveryAddress;
}
