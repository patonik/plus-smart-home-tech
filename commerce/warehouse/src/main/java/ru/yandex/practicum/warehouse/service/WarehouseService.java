package ru.yandex.practicum.warehouse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.interaction.domain.WarehouseProduct;
import ru.yandex.practicum.interaction.dto.cart.BookedProductsDto;
import ru.yandex.practicum.interaction.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.interaction.dto.warehouse.AddProductToWarehouseRequest;
import ru.yandex.practicum.interaction.dto.warehouse.AddressDto;
import ru.yandex.practicum.interaction.dto.warehouse.NewProductInWarehouseRequest;
import ru.yandex.practicum.interaction.mapper.AddressMapper;
import ru.yandex.practicum.interaction.mapper.WarehouseProductMapper;
import ru.yandex.practicum.warehouse.exception.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.warehouse.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.warehouse.exception.SpecifiedProductAlreadyInWarehouseException;
import ru.yandex.practicum.warehouse.repository.AddressRepository;
import ru.yandex.practicum.warehouse.repository.WarehouseRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final AddressRepository addressRepository;

    @Transactional
    public void addNewProduct(NewProductInWarehouseRequest request) {
        UUID productId = request.getProductId();
        Optional<WarehouseProduct> existingProduct = warehouseRepository.findById(productId);
        if (existingProduct.isPresent()) {
            throw new SpecifiedProductAlreadyInWarehouseException(productId);
        }
        warehouseRepository.save(WarehouseProductMapper.convertToEntity(request));
    }

    @Transactional
    public void addProductStock(AddProductToWarehouseRequest request) {
        WarehouseProduct product = warehouseRepository.findById(request.getProductId())
            .orElseThrow(() -> new NoSpecifiedProductInWarehouseException(request.getProductId()));

        product.setQuantity(product.getQuantity() + request.getQuantityToAdd());
        warehouseRepository.save(product);
    }

    @Transactional(readOnly = true)
    public BookedProductsDto checkProductAvailability(ShoppingCartDto shoppingCart) {
        Map<UUID, Integer> products = shoppingCart.getProducts();
        List<WarehouseProduct> warehouseProducts =
            warehouseRepository.findAllWhereProductIdIn(products.keySet().stream().toList());
        if (warehouseProducts.size() != products.size()) {
            warehouseProducts.stream().map(WarehouseProduct::getProductId).forEach(products.keySet()::remove);
            throw new NoSpecifiedProductInWarehouseException(products.keySet().stream().findFirst().orElseThrow(() ->
                new RuntimeException("Logical error in data retrieval")));
        }
        boolean fragile = false;
        double weight = 0;
        double volume = 0;
        for (WarehouseProduct warehouseProduct : warehouseProducts) {
            Integer actualQuantity = warehouseProduct.getQuantity();
            UUID productId = warehouseProduct.getProductId();
            Integer requestedQuantity = products.get(productId);
            if (actualQuantity < requestedQuantity) {
                throw new ProductInShoppingCartLowQuantityInWarehouse(productId, requestedQuantity, actualQuantity);
            }
            if (warehouseProduct.getFragile()) {
                fragile = true;
            }
            weight += warehouseProduct.getWeight();
            volume += warehouseProduct.getDimension().getVolume();
        }
        BookedProductsDto bookedProductsDto = new BookedProductsDto();
        bookedProductsDto.setFragile(fragile);
        bookedProductsDto.setDeliveryWeight(weight);
        bookedProductsDto.setDeliveryVolume(volume);
        return bookedProductsDto;
    }

    @Transactional(readOnly = true)
    public AddressDto getWarehouseAddress() {
        return AddressMapper.convertToDto(addressRepository.findAll().getFirst());
    }
}

