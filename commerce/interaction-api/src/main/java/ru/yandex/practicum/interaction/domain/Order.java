package ru.yandex.practicum.interaction.domain;


import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.yandex.practicum.interaction.enums.OrderState;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID orderId;

    @Column(name = "shopping_cart_id")
    private UUID shoppingCartId;
    @NotNull
    @Column(name = "user_id")
    private String userId;

    @ElementCollection
    @CollectionTable(name = "order_products", joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<UUID, Integer> products;

    @Column(name = "payment_id")
    private UUID paymentId;

    @Column(name = "delivery_id")
    private UUID deliveryId;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private OrderState state;

    @Column(name = "delivery_weight")
    private Double deliveryWeight;

    @Column(name = "delivery_volume")
    private Double deliveryVolume;

    @Column(name = "fragile")
    private Boolean fragile;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "delivery_price")
    private Double deliveryPrice;

    @Column(name = "product_price")
    private Double productPrice;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addressId")
    private Address deliveryAddress;

    public void addOrUpdateProduct(UUID productId, int quantity) {
        if (quantity <= 0) {
            products.remove(productId);
        } else {
            products.put(productId, products.getOrDefault(productId, 0) + quantity);
        }
    }

    public Integer returnProduct(UUID productId, int returnQuantity) {
        if (returnQuantity <= 0) {
            throw new RuntimeException("Return quantity must be greater than 0");
        }
        if (!products.containsKey(productId)) {
            return null;
        }
        int quantity = products.get(productId);
        if (returnQuantity >= quantity) {
            products.remove(productId);
        } else {
            products.put(productId, quantity - returnQuantity);
        }
        return Math.min(returnQuantity, quantity);
    }

    public void removeProduct(UUID productId) {
        products.remove(productId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(orderId);
    }

    @Override
    public String toString() {
        return "Order{" +
            "orderId=" + orderId +
            ", shoppingCartId=" + shoppingCartId +
            ", userId='" + userId + '\'' +
            ", paymentId=" + paymentId +
            ", deliveryId=" + deliveryId +
            ", state=" + state +
            ", deliveryWeight=" + deliveryWeight +
            ", deliveryVolume=" + deliveryVolume +
            ", fragile=" + fragile +
            ", totalPrice=" + totalPrice +
            ", deliveryPrice=" + deliveryPrice +
            ", productPrice=" + productPrice +
            ", deliveryAddress=" + deliveryAddress +
            '}';
    }
}
