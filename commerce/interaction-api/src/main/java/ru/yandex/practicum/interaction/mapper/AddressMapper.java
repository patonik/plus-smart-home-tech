package ru.yandex.practicum.interaction.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.interaction.domain.Address;
import ru.yandex.practicum.interaction.dto.warehouse.AddressDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressMapper {
    AddressDto convertToDto(Address entity);

    Address convertToEntity(AddressDto dto);
}