package ru.yandex.practicum.interaction.mapper;

import ru.yandex.practicum.interaction.domain.ShoppingCart;
import ru.yandex.practicum.interaction.dto.cart.ShoppingCartDto;

public class CartMapper {

    public static ShoppingCartDto convertToDto(ShoppingCart entity) {
        if (entity == null) {
            return null;
        }

        return new ShoppingCartDto(
            entity.getShoppingCartId(),
            entity.getUserId(),
            entity.getProducts()
        );
    }

    public static ShoppingCart convertToEntity(ShoppingCartDto dto) {
        if (dto == null) {
            return null;
        }

        ShoppingCart entity = new ShoppingCart();
        entity.setShoppingCartId(dto.getShoppingCartId());
        entity.setUserId(dto.getUserId());
        entity.setProducts(dto.getProducts());
        return entity;
    }

}
