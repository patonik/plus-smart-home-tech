package ru.yandex.practicum.warehouse.exception;


import java.util.UUID;

public class ProductInShoppingCartLowQuantityInWarehouse extends RuntimeException {

    public ProductInShoppingCartLowQuantityInWarehouse(UUID productId, int requestedQuantity,
                                                       int availableQuantity) {
        super(String.format(
            "Product with ID %s has insufficient stock in the warehouse. Requested: %d, Available: %d.",
            productId, requestedQuantity, availableQuantity
        ));
    }
}

