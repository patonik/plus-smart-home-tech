package ru.yandex.practicum.interaction.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import ru.yandex.practicum.interaction.domain.WarehouseProduct;
import ru.yandex.practicum.interaction.dto.warehouse.AddProductToWarehouseRequest;
import ru.yandex.practicum.interaction.dto.warehouse.NewProductInWarehouseRequest;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WarehouseProductMapper {

    WarehouseProduct convertToEntity(NewProductInWarehouseRequest dto);

    void updateEntity(AddProductToWarehouseRequest dto, @MappingTarget WarehouseProduct entity);
}
