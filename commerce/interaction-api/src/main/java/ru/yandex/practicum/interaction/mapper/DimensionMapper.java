package ru.yandex.practicum.interaction.mapper;

import ru.yandex.practicum.interaction.domain.Dimension;
import ru.yandex.practicum.interaction.dto.warehouse.DimensionDto;

public class DimensionMapper {
    public static DimensionDto convertToDto(Dimension entity) {
        if (entity == null) {
            return null;
        }

        return new DimensionDto(
            entity.getWidth(),
            entity.getHeight(),
            entity.getDepth()
        );
    }

    public static Dimension convertToEntity(DimensionDto dto) {
        if (dto == null) {
            return null;
        }

        Dimension entity = new Dimension();
        entity.setDepth(dto.getDepth());
        entity.setWidth(dto.getWidth());
        entity.setHeight(dto.getHeight());

        return entity;
    }
}
