package ru.yandex.practicum.interaction.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.practicum.interaction.dto.cart.BookedProductsDto;
import ru.yandex.practicum.interaction.dto.cart.ChangeProductQuantityRequest;
import ru.yandex.practicum.interaction.dto.cart.ShoppingCartDto;

import java.util.Map;
import java.util.UUID;

@FeignClient(name = "shopping-cart-service", path = "/api/v1/shopping-cart")
public interface ShoppingCartClient {

    @GetMapping
    ResponseEntity<ShoppingCartDto> getShoppingCart(@RequestParam String userId);

    @PutMapping
    ResponseEntity<ShoppingCartDto> addProducts(
        @RequestParam String userId,
        @RequestBody Map<UUID, Integer> products);

    @DeleteMapping
    ResponseEntity<Void> deleteShoppingCart(@RequestParam String userId);

    @PostMapping("/remove")
    ResponseEntity<Void> retainProducts(
        @RequestParam String userId,
        @RequestBody Map<UUID, Integer> products);

    @PostMapping("/change-quantity")
    ResponseEntity<ShoppingCartDto> changeProductQuantity(
        @RequestParam String userId,
        @RequestBody @Validated ChangeProductQuantityRequest changeProductQuantityRequest);

    @PostMapping("/booking")
    ResponseEntity<BookedProductsDto> bookProducts(@RequestParam String userId);
}

