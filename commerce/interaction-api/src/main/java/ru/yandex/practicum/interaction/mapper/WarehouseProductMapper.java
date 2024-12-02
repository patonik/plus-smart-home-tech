package ru.yandex.practicum.interaction.mapper;

import ru.yandex.practicum.interaction.domain.WarehouseProduct;
import ru.yandex.practicum.interaction.dto.warehouse.AddProductToWarehouseRequest;
import ru.yandex.practicum.interaction.dto.warehouse.NewProductInWarehouseRequest;

public class WarehouseProductMapper {

    public static WarehouseProduct convertToEntity(NewProductInWarehouseRequest dto) {
        if (dto == null) {
            return null;
        }

        WarehouseProduct entity = new WarehouseProduct();
        entity.setProductId(dto.getProductId());
        entity.setWeight(dto.getWeight());
        entity.setFragile(dto.getFragile());
        entity.setDimension(DimensionMapper.convertToEntity(dto.getDimension()));

        return entity;
    }

    public static void updateEntity(AddProductToWarehouseRequest dto, WarehouseProduct entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setQuantity(dto.getQuantityToAdd());
    }
}
