package ru.yandex.practicum.interaction.mapper;


import ru.yandex.practicum.interaction.domain.Address;
import ru.yandex.practicum.interaction.dto.warehouse.AddressDto;

public class AddressMapper {
    public static AddressDto convertToDto(Address entity) {
        if (entity == null) {
            return null;
        }

        return new AddressDto(
            entity.getCountry(),
            entity.getCity(),
            entity.getStreet(),
            entity.getHouse(),
            entity.getFlat()
        );
    }

    public static Address convertToEntity(AddressDto dto) {
        if (dto == null) {
            return null;
        }

        Address entity = new Address();
        entity.setCity(dto.getCity());
        entity.setCountry(dto.getCountry());
        entity.setFlat(dto.getFlat());
        entity.setStreet(dto.getStreet());
        entity.setHouse(dto.getHouse());

        return entity;
    }
}
