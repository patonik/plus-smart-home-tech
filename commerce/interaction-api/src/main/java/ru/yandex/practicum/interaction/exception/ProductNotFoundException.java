package ru.yandex.practicum.interaction.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Getter
public class ProductNotFoundException extends RuntimeException {

    private final String productId;

    public ProductNotFoundException(String productId) {
        super(String.format("Product with ID '%s' not found.", productId));
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "ProductNotFoundException{" +
            "productId='" + productId + '\'' +
            ", message='" + getMessage() + '\'' +
            '}';
    }
}
