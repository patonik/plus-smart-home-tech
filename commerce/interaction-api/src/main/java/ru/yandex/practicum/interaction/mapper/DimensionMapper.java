package ru.yandex.practicum.interaction.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.interaction.domain.Dimension;
import ru.yandex.practicum.interaction.dto.warehouse.DimensionDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DimensionMapper {
    DimensionDto convertToDto(Dimension entity);

    Dimension convertToEntity(DimensionDto dto);
}
