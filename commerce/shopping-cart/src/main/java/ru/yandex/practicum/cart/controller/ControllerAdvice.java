package ru.yandex.practicum.cart.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.interaction.exception.NoProductsInShoppingCartException;
import ru.yandex.practicum.interaction.exception.NotAuthorizedUserException;
import ru.yandex.practicum.interaction.exception.ValidationException;

@RestControllerAdvice
public class ControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ValidationException handleConstraintViolationException(final ConstraintViolationException e) {
        return new ValidationException(e.getMessage());
    }
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotAuthorizedUserException.class)
    public NotAuthorizedUserException handleConstraintViolationException(final NotAuthorizedUserException e) {
        return e;
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoProductsInShoppingCartException.class)
    public NoProductsInShoppingCartException handleConstraintViolationException(final NoProductsInShoppingCartException e) {
        return e;
    }
}
