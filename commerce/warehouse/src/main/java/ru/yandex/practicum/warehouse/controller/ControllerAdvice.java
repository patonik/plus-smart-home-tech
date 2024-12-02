package ru.yandex.practicum.warehouse.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.interaction.exception.ValidationException;
import ru.yandex.practicum.warehouse.exception.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.warehouse.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.warehouse.exception.SpecifiedProductAlreadyInWarehouseException;

@RestControllerAdvice
public class ControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ValidationException handleConstraintViolationException(final ConstraintViolationException e) {
        return new ValidationException(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SpecifiedProductAlreadyInWarehouseException.class)
    public SpecifiedProductAlreadyInWarehouseException handleConstraintViolationException(
        final SpecifiedProductAlreadyInWarehouseException e) {
        return e;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProductInShoppingCartLowQuantityInWarehouse.class)
    public ProductInShoppingCartLowQuantityInWarehouse handleConstraintViolationException(
        final ProductInShoppingCartLowQuantityInWarehouse e) {
        return e;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSpecifiedProductInWarehouseException.class)
    public NoSpecifiedProductInWarehouseException handleConstraintViolationException(
        final NoSpecifiedProductInWarehouseException e) {
        return e;
    }
}
