package ru.yandex.practicum.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.cart.service.ShoppingCartService;
import ru.yandex.practicum.interaction.dto.cart.BookedProductsDto;
import ru.yandex.practicum.interaction.dto.cart.ChangeProductQuantityRequest;
import ru.yandex.practicum.interaction.dto.cart.ShoppingCartDto;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/shopping-cart")
@RequiredArgsConstructor
@Validated
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    public ResponseEntity<ShoppingCartDto> getShoppingCart(@RequestParam String userId) {
        ShoppingCartDto shoppingCart = shoppingCartService.getShoppingCart(userId);
        return ResponseEntity.ok(shoppingCart);
    }

    @PutMapping
    public ResponseEntity<ShoppingCartDto> addProducts(
        @RequestParam String userId,
        @RequestBody Map<UUID, Integer> products) {
        ShoppingCartDto updatedCart = shoppingCartService.addProducts(userId, products);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteShoppingCart(@RequestParam String userId) {
        shoppingCartService.deleteShoppingCart(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/remove")
    public ResponseEntity<Void> retainProducts(@RequestParam String userId,
                                               @RequestBody Map<UUID, Integer> products) {
        shoppingCartService.retainProducts(userId, products);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/change-quantity")
    public ResponseEntity<ShoppingCartDto> changeProductQuantity(
        @RequestParam String userId,
        @RequestBody @Validated ChangeProductQuantityRequest changeProductQuantityRequest) {
        ShoppingCartDto updatedCart = shoppingCartService.changeProductQuantity(userId, changeProductQuantityRequest);
        return ResponseEntity.ok(updatedCart);
    }

    @PostMapping("/booking")
    public ResponseEntity<BookedProductsDto> bookProducts(@RequestParam String userId) {
        BookedProductsDto bookedProducts = shoppingCartService.bookProducts(userId);
        return ResponseEntity.ok(bookedProducts);
    }
}


