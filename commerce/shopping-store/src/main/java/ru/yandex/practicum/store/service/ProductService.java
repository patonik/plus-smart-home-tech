package ru.yandex.practicum.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.interaction.domain.Product;
import ru.yandex.practicum.interaction.dto.product.ProductDto;
import ru.yandex.practicum.interaction.dto.product.SetProductQuantityStateRequest;
import ru.yandex.practicum.interaction.enums.ProductCategory;
import ru.yandex.practicum.interaction.enums.QuantityState;
import ru.yandex.practicum.interaction.mapper.ProductMapper;
import ru.yandex.practicum.store.exception.ProductNotFoundException;
import ru.yandex.practicum.store.repository.ProductRepository;

import java.util.List;
import java.util.UUID;

import static ru.yandex.practicum.interaction.mapper.ProductMapper.convertToDto;
import static ru.yandex.practicum.interaction.mapper.ProductMapper.convertToEntity;
import static ru.yandex.practicum.interaction.mapper.ProductMapper.updateEntity;


@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductService {
    private final ProductRepository productRepository;

    public Page<ProductDto> getProducts(ProductCategory category, int page, int size, List<String> sort) {
        // Build the sorting criteria
        Sort sortOrder = Sort.unsorted();
        if (sort != null && !sort.isEmpty()) {
            sortOrder = sort.stream()
                .map(this::parseSortString)
                .reduce(Sort::and)
                .orElse(Sort.unsorted());
        }

        PageRequest pageRequest = PageRequest.of(page, size, sortOrder);

        Page<Product> productPage = productRepository.findAllByProductCategory(category, pageRequest);
        return productPage.map(ProductMapper::convertToDto);
    }

    public ProductDto createProduct(ProductDto productDto) {
        Product product = convertToEntity(productDto);
        product = productRepository.save(product);
        return convertToDto(product);
    }

    public ProductDto updateProduct(ProductDto productDto) {
        Product product = productRepository.findById(productDto.getProductId())
            .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        updateEntity(productDto, product);
        product = productRepository.save(product);
        return convertToDto(product);
    }

    public void deleteProduct(UUID productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        productRepository.delete(product);
    }

    public Boolean setProductQuantityState(SetProductQuantityStateRequest quantityStateRequest) {
        Product product = productRepository.findById(quantityStateRequest.getProductId())
            .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        QuantityState quantityState = product.getQuantityState();
        QuantityState requestQuantityState = quantityStateRequest.getQuantityState();
        if (quantityState.equals(requestQuantityState)) {
            return true;
        }
        product.setQuantityState(requestQuantityState);
        return true;
    }

    public ProductDto getProductById(UUID productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        return convertToDto(product);
    }

    private Sort parseSortString(String sortString) {
        String[] parts = sortString.split(",");
        String property = parts[0];
        Sort.Direction direction = parts.length > 1 && parts[1].equalsIgnoreCase("desc")
            ? Sort.Direction.DESC
            : Sort.Direction.ASC;
        return Sort.by(direction, property);
    }
}

