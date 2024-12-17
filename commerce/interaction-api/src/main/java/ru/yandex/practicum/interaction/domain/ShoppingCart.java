package ru.yandex.practicum.interaction.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "shopping_cart")
@Getter
@Setter
@NoArgsConstructor
public class ShoppingCart {

    @Id
    @GeneratedValue
    private UUID shoppingCartId;

    @Column(nullable = false)
    private String userId;

    @ElementCollection
    @CollectionTable(name = "shopping_cart_products",
        joinColumns = @JoinColumn(name = "shopping_cart_id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<UUID, Integer> products = new HashMap<>();

    public ShoppingCart(String userId) {
        this.userId = userId;
    }

    public void addOrUpdateProduct(UUID productId, int quantity) {
        if (quantity <= 0) {
            products.remove(productId);
        } else {
            products.put(productId, products.getOrDefault(productId, 0) + quantity);
        }
    }

    public void removeProduct(UUID productId) {
        products.remove(productId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCart cart = (ShoppingCart) o;
        return Objects.equals(shoppingCartId, cart.shoppingCartId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(shoppingCartId);
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
            "shoppingCartId=" + shoppingCartId +
            ", userId='" + userId + '\'' +
            '}';
    }
}


