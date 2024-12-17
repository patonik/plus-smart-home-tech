package ru.yandex.practicum.interaction.exception;

import java.util.UUID;

public class SpecifiedProductAlreadyInWarehouseException extends RuntimeException {

    public SpecifiedProductAlreadyInWarehouseException(UUID productId) {
        super(String.format("Product with ID %s is already registered in the warehouse.", productId));
    }
}

