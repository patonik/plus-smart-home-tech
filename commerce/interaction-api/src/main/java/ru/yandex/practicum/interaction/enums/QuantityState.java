package ru.yandex.practicum.interaction.enums;

/**
 * Enum representing the quantity status of a product.
 */
public enum QuantityState {
    ENDED,  // Product is out of stock
    FEW,    // Low stock availability
    ENOUGH, // Moderate stock availability
    MANY    // High stock availability
}

