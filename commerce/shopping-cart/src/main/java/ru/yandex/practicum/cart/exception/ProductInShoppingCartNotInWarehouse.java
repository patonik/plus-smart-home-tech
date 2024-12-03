package ru.yandex.practicum.cart.exception;

public class ProductInShoppingCartNotInWarehouse extends RuntimeException{
    public ProductInShoppingCartNotInWarehouse(String message) {
        super(message);
    }
}
