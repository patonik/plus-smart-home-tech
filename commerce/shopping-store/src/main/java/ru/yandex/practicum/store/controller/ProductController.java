package ru.yandex.practicum.store.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.interaction.client.ProductClient;
import ru.yandex.practicum.interaction.dto.product.ProductDto;
import ru.yandex.practicum.interaction.dto.product.SetProductQuantityStateRequest;
import ru.yandex.practicum.interaction.enums.ProductCategory;
import ru.yandex.practicum.store.service.ProductService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/shopping-store")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductController implements ProductClient {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductDto>> getProducts(
        @RequestParam ProductCategory category,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) List<String> sort) {
        Page<ProductDto> products = productService.getProducts(category, page, size, sort);
        return ResponseEntity.ok(products);
    }

    @PutMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.createProduct(productDto));
    }

    @PostMapping
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.updateProduct(productDto));
    }

    @PostMapping("/removeProductFromStore")
    public ResponseEntity<Void> deleteProduct(@RequestBody UUID productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/quantity-state")
    public ResponseEntity<Boolean> setProductQuantityState(
        @Valid @RequestBody SetProductQuantityStateRequest quantityStateRequest) {
        Boolean updatedProduct = productService.setProductQuantityState(quantityStateRequest);
        return ResponseEntity.ok(updatedProduct);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable UUID productId) {
        ProductDto product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }
}

