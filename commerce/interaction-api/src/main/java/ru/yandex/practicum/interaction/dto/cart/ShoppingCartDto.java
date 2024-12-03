package ru.yandex.practicum.interaction.dto.cart;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartDto {
    @NotNull
    private UUID shoppingCartId;
    @NotNull
    private String userId;
    private Map<UUID, Integer> products;
}


