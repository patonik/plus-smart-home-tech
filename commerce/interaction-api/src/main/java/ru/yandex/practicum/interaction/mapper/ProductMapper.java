package ru.yandex.practicum.interaction.mapper;


import ru.yandex.practicum.interaction.domain.Product;
import ru.yandex.practicum.interaction.dto.product.ProductDto;

public class ProductMapper {

    public static ProductDto convertToDto(Product entity) {
        if (entity == null) {
            return null;
        }

        return new ProductDto(
            entity.getProductId(),
            entity.getProductName(),
            entity.getDescription(),
            entity.getImageSrc(),
            entity.getProductCategory(),
            entity.getProductState(),
            entity.getQuantityState(),
            entity.getRating(),
            entity.getPrice()
        );
    }

    public static Product convertToEntity(ProductDto dto) {
        if (dto == null) {
            return null;
        }

        Product entity = new Product();
        entity.setProductId(dto.getProductId());
        entity.setProductName(dto.getProductName());
        entity.setDescription(dto.getDescription());
        entity.setImageSrc(dto.getImageSrc());
        entity.setPrice(dto.getPrice());
        entity.setRating(dto.getRating());
        entity.setProductCategory(dto.getProductCategory());
        entity.setProductState(dto.getProductState());
        entity.setQuantityState(dto.getQuantityState());

        return entity;
    }

    public static void updateEntity(ProductDto dto, Product entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setProductName(dto.getProductName());
        entity.setDescription(dto.getDescription());
        entity.setImageSrc(dto.getImageSrc());
        entity.setPrice(dto.getPrice());
        entity.setRating(dto.getRating());
        entity.setProductCategory(dto.getProductCategory());
        entity.setProductState(dto.getProductState());
        entity.setQuantityState(dto.getQuantityState());
    }
}

