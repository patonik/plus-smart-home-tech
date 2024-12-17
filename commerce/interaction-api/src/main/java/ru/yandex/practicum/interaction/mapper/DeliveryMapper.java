package ru.yandex.practicum.interaction.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.interaction.domain.Delivery;
import ru.yandex.practicum.interaction.dto.delivery.DeliveryDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DeliveryMapper {

    DeliveryDto convertToDto(Delivery entity);

    Delivery convertToEntity(DeliveryDto dto);

}
