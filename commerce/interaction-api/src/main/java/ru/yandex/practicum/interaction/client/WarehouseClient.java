package ru.yandex.practicum.interaction.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.interaction.dto.cart.BookedProductsDto;
import ru.yandex.practicum.interaction.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.interaction.dto.warehouse.AddProductToWarehouseRequest;
import ru.yandex.practicum.interaction.dto.warehouse.AddressDto;
import ru.yandex.practicum.interaction.dto.warehouse.NewProductInWarehouseRequest;

@FeignClient(name = "warehouse-service", path = "/api/v1/warehouse")
public interface WarehouseClient {

    @PutMapping
    ResponseEntity<Void> newProductInWarehouse(@RequestBody NewProductInWarehouseRequest request);

    @PostMapping("/check")
    ResponseEntity<BookedProductsDto> checkProductQuantityEnoughForShoppingCart(
        @RequestBody ShoppingCartDto shoppingCart);

    @PostMapping("/add")
    ResponseEntity<Void> addProductToWarehouse(@RequestBody AddProductToWarehouseRequest request);

    @GetMapping("/address")
    ResponseEntity<AddressDto> getWarehouseAddress();
}

