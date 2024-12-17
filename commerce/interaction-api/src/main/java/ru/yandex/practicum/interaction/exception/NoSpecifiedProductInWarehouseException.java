package ru.yandex.practicum.interaction.exception;

import java.util.UUID;

public class NoSpecifiedProductInWarehouseException extends RuntimeException {

    public NoSpecifiedProductInWarehouseException(UUID productId) {
        super(String.format("Product with ID %s is not available in the warehouse.", productId));
    }
}

