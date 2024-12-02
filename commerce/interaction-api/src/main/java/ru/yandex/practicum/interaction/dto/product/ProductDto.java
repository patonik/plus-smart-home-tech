package ru.yandex.practicum.interaction.dto.product;


import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yandex.practicum.interaction.enums.ProductCategory;
import ru.yandex.practicum.interaction.enums.ProductState;
import ru.yandex.practicum.interaction.enums.QuantityState;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ProductDto {

    private UUID productId; // Unique identifier for the product
    private String productName; // Name of the product
    private String description; // Description of the product
    private String imageSrc; // Image URL for the product
    private ProductCategory productCategory; // Category of the product
    private ProductState productState; // Active/Deactivated state of the product
    private QuantityState quantityState; // Stock availability of the product
    private double rating; // Customer rating of the product (1 to 5)
    private double price; // Price of the product
}

