package ru.yandex.practicum.interaction.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.interaction.domain.ShoppingCart;
import ru.yandex.practicum.interaction.dto.cart.ShoppingCartDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CartMapper {

    ShoppingCartDto convertToDto(ShoppingCart entity);

    ShoppingCart convertToEntity(ShoppingCartDto dto);

}
