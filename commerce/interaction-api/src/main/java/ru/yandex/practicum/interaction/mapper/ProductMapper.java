package ru.yandex.practicum.interaction.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import ru.yandex.practicum.interaction.domain.Product;
import ru.yandex.practicum.interaction.dto.product.ProductDto;
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    ProductDto convertToDto(Product entity);

    Product convertToEntity(ProductDto dto);

    void updateEntity(ProductDto dto, @MappingTarget Product entity);
}

