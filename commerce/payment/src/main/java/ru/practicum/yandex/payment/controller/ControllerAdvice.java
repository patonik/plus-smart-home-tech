package ru.practicum.yandex.payment.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.yandex.payment.exception.NoPaymentFoundException;
import ru.practicum.yandex.payment.exception.NotEnoughInfoInOrderToCalculateException;
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
    @ExceptionHandler(NotEnoughInfoInOrderToCalculateException.class)
    public NotEnoughInfoInOrderToCalculateException handleConstraintViolationException(
        final NotEnoughInfoInOrderToCalculateException e) {
        return e;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoPaymentFoundException.class)
    public NoPaymentFoundException handleConstraintViolationException(final NoPaymentFoundException e) {
        return e;
    }
}
