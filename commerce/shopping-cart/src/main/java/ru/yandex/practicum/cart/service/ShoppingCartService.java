package ru.yandex.practicum.cart.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.cart.exception.NoProductsInShoppingCartException;
import ru.yandex.practicum.cart.exception.NotAuthorizedUserException;
import ru.yandex.practicum.cart.repository.ShoppingCartRepository;
import ru.yandex.practicum.interaction.client.WarehouseClient;
import ru.yandex.practicum.interaction.domain.ShoppingCart;
import ru.yandex.practicum.interaction.dto.cart.BookedProductsDto;
import ru.yandex.practicum.interaction.dto.cart.ChangeProductQuantityRequest;
import ru.yandex.practicum.interaction.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.interaction.mapper.CartMapper;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static ru.yandex.practicum.interaction.mapper.CartMapper.convertToDto;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final WarehouseClient warehouseClient;

    public ShoppingCartDto getShoppingCart(String userId) {
        if (userId == null) {
            throw new NotAuthorizedUserException("User not authorized");
        }
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId)
            .orElse(shoppingCartRepository.save(new ShoppingCart(userId)));
        return convertToDto(cart);
    }

    public ShoppingCartDto addProducts(String userId, Map<UUID, Integer> products) {
        if (userId == null) {
            throw new NotAuthorizedUserException("User not authorized");
        }
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId)
            .orElse(shoppingCartRepository.save(new ShoppingCart(userId)));
        for (UUID uuid : products.keySet()) {
            cart.addOrUpdateProduct(uuid, products.get(uuid));
        }
        shoppingCartRepository.save(cart);
        return convertToDto(cart);
    }

    public void deleteShoppingCart(String userId) {
        if (userId == null) {
            throw new NotAuthorizedUserException("User not authorized");
        }
        Optional<ShoppingCart> cart = shoppingCartRepository.findByUserId(userId);
        cart.ifPresent(shoppingCartRepository::delete);
    }

    public void retainProducts(String userId, Map<UUID, Integer> products) {
        if (userId == null) {
            throw new NotAuthorizedUserException("User not authorized");
        }
        Optional<ShoppingCart> cart = shoppingCartRepository.findByUserId(userId);
        if (cart.isPresent()) {
            if (!products.keySet().containsAll(products.keySet())) {
                throw new NoProductsInShoppingCartException("No such products in cart");
            }
            ShoppingCart cartValue = cart.get();
            cartValue.setProducts(products);
            shoppingCartRepository.save(cartValue);
        }
    }

    public ShoppingCartDto changeProductQuantity(String userId,
                                                 ChangeProductQuantityRequest changeProductQuantityRequest) {
        if (userId == null) {
            throw new NotAuthorizedUserException("User not authorized");
        }
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId).orElseThrow(
            () -> new RuntimeException("Shopping cart not found")
        );
        Map<UUID, Integer> products = cart.getProducts();
        UUID productId = changeProductQuantityRequest.getProductId();
        if (!products.containsKey(productId)) {
            throw new NoProductsInShoppingCartException("No such products in cart");
        }
        cart.addOrUpdateProduct(productId, changeProductQuantityRequest.getQuantity());
        shoppingCartRepository.save(cart);
        return convertToDto(cart);
    }

    public BookedProductsDto bookProducts(String userId) {
        if (userId == null) {
            throw new NotAuthorizedUserException("User not authorized");
        }
        ShoppingCart cart = shoppingCartRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Shopping cart not found for ID: " + userId));
        BookedProductsDto bookedProducts;
        try {
            bookedProducts = warehouseClient.checkProductQuantityEnoughForShoppingCart(CartMapper.convertToDto(cart)).getBody();
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("Product availability check failed: " + e.getMessage());
        }
        return bookedProducts;
    }
}

