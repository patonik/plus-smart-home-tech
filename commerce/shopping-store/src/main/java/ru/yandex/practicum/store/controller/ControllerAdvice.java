package ru.yandex.practicum.store.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.interaction.exception.ProductNotFoundException;
import ru.yandex.practicum.interaction.exception.ValidationException;

@RestControllerAdvice
public class ControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ValidationException handleConstraintViolationException(final ConstraintViolationException e) {
        return new ValidationException(e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public ProductNotFoundException handleConstraintViolationException(final ProductNotFoundException e) {
        return e;
    }
}
