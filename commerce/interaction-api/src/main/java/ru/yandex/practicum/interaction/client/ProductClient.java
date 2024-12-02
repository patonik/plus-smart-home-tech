package ru.yandex.practicum.interaction.client;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.practicum.interaction.dto.product.ProductDto;
import ru.yandex.practicum.interaction.dto.product.SetProductQuantityStateRequest;
import ru.yandex.practicum.interaction.enums.ProductCategory;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "shopping-store-service", path = "/api/v1/shopping-store")
public interface ProductClient {

    @GetMapping
    ResponseEntity<Page<ProductDto>> getProducts(
        @RequestParam ProductCategory category,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) List<String> sort);

    @PutMapping
    ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto);

    @PostMapping
    ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto);

    @PostMapping("/removeProductFromStore")
    ResponseEntity<Void> deleteProduct(@RequestBody UUID productId);

    @PatchMapping("/quantity-state")
    ResponseEntity<Boolean> setProductQuantityState(
        @Valid @RequestBody SetProductQuantityStateRequest quantityStateRequest);

    @GetMapping("/{productId}")
    ResponseEntity<ProductDto> getProductById(@PathVariable UUID productId);
}

