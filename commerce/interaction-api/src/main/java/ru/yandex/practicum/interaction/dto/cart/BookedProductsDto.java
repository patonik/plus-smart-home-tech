package ru.yandex.practicum.interaction.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookedProductsDto {
    private Double deliveryWeight;
    private Double deliveryVolume;
    private Boolean fragile;
}


