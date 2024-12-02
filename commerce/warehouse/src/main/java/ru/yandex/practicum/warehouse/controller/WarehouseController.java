package ru.yandex.practicum.warehouse.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.interaction.client.WarehouseClient;
import ru.yandex.practicum.interaction.dto.cart.BookedProductsDto;
import ru.yandex.practicum.interaction.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.interaction.dto.warehouse.AddProductToWarehouseRequest;
import ru.yandex.practicum.interaction.dto.warehouse.AddressDto;
import ru.yandex.practicum.interaction.dto.warehouse.NewProductInWarehouseRequest;
import ru.yandex.practicum.warehouse.service.WarehouseService;

@RestController
@RequestMapping("/api/v1/warehouse")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WarehouseController implements WarehouseClient {
    private final WarehouseService warehouseService;

    @PutMapping
    public ResponseEntity<Void> newProductInWarehouse(@RequestBody NewProductInWarehouseRequest request) {
        warehouseService.addNewProduct(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/check")
    public ResponseEntity<BookedProductsDto> checkProductQuantityEnoughForShoppingCart(
        @RequestBody ShoppingCartDto shoppingCart) {
        BookedProductsDto bookedProducts = warehouseService.checkProductAvailability(shoppingCart);
        return ResponseEntity.ok(bookedProducts);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addProductToWarehouse(@RequestBody AddProductToWarehouseRequest request) {
        warehouseService.addProductStock(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/address")
    public ResponseEntity<AddressDto> getWarehouseAddress() {
        AddressDto address = warehouseService.getWarehouseAddress();
        return ResponseEntity.ok(address);
    }
}

