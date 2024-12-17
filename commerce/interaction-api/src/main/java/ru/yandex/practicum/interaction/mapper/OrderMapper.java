package ru.yandex.practicum.interaction.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.interaction.domain.Order;
import ru.yandex.practicum.interaction.dto.order.CreateNewOrderRequest;
import ru.yandex.practicum.interaction.dto.order.OrderDto;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper extends AddressMapper{

    OrderDto convertToDto(Order entity);
    @Mapping(target = "userName", source = "userName")
    Order convertToEntity(OrderDto dto, String userName);

    List<OrderDto> toDtoList(List<Order> orders);
    @Mapping(target = "shoppingCartId", source = "shoppingCart.shoppingCartId")
    @Mapping(target = "userId", source = "shoppingCart.userId")
    @Mapping(target = "products", source = "shoppingCart.products")
    Order fromCreateRequest(CreateNewOrderRequest request);
}
