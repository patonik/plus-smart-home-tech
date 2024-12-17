package ru.yandex.practicum.interaction.dto.warehouse;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssemblyProductsForOrderRequest {
    @NotNull
    private UUID orderId;
    @NotNull
    private Map<UUID, Integer> products;
}