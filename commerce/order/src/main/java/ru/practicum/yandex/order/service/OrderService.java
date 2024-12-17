package ru.practicum.yandex.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.yandex.order.exception.NoOrderFoundException;
import ru.practicum.yandex.order.repository.OrderRepository;
import ru.yandex.practicum.interaction.domain.Order;
import ru.yandex.practicum.interaction.dto.order.CreateNewOrderRequest;
import ru.yandex.practicum.interaction.dto.order.OrderDto;
import ru.yandex.practicum.interaction.dto.order.ProductReturnRequest;
import ru.yandex.practicum.interaction.enums.OrderState;
import ru.yandex.practicum.interaction.exception.ProductNotFoundException;
import ru.yandex.practicum.interaction.mapper.OrderMapper;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public List<OrderDto> getOrdersByUsername(String username) {
        List<Order> orders = orderRepository.findByUserId(username);
        return orderMapper.toDtoList(orders);
    }

    public OrderDto createOrder(CreateNewOrderRequest request) {
        Order order = orderMapper.fromCreateRequest(request);
        order.setState(OrderState.NEW);
        order = orderRepository.save(order);
        return orderMapper.convertToDto(order);
    }

    public OrderDto returnProducts(ProductReturnRequest request) {
        UUID orderId = request.getOrderId();
        Map<UUID, Integer> products = request.getProducts();
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new NoOrderFoundException("Order not found: " + orderId));
        for (UUID uuid : products.keySet()) {
            Integer returnedAmount = order.returnProduct(uuid, products.get(uuid));
            if (returnedAmount == null) {
                throw new ProductNotFoundException("Product not found in order: " + uuid);
            }
        }
        order.setState(OrderState.PRODUCT_RETURNED);
        order = orderRepository.save(order);
        return orderMapper.convertToDto(order);
    }

    public OrderDto processPayment(UUID orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new NoOrderFoundException("Order not found: " + orderId));
        order.setState(OrderState.PAID);
        order = orderRepository.save(order);
        return orderMapper.convertToDto(order);
    }

    public OrderDto handlePaymentFailure(UUID orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new NoOrderFoundException("Order not found: " + orderId));
        order.setState(OrderState.PAYMENT_FAILED);
        order = orderRepository.save(order);
        return orderMapper.convertToDto(order);
    }

    public OrderDto processDelivery(UUID orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new NoOrderFoundException("Order not found: " + orderId));
        order.setState(OrderState.DELIVERED);
        order = orderRepository.save(order);
        return orderMapper.convertToDto(order);
    }

    public OrderDto handleDeliveryFailure(UUID orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new NoOrderFoundException("Order not found: " + orderId));
        order.setState(OrderState.DELIVERY_FAILED);
        order = orderRepository.save(order);
        return orderMapper.convertToDto(order);
    }

    public OrderDto completeOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new NoOrderFoundException("Order not found: " + orderId));
        order.setState(OrderState.COMPLETED);
        order = orderRepository.save(order);
        return orderMapper.convertToDto(order);
    }

    public OrderDto calculateTotalCost(UUID orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new NoOrderFoundException("Order not found: " + orderId));
        double totalCost = (order.getProductPrice() != null ? order.getProductPrice() : 0.0)
            + (order.getDeliveryPrice() != null ? order.getDeliveryPrice() : 0.0);
        order.setTotalPrice(totalCost);
        order = orderRepository.save(order);
        return orderMapper.convertToDto(order);
    }

    public OrderDto calculateDeliveryCost(UUID orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new NoOrderFoundException("Order not found: " + orderId));
        double deliveryCost = calculateDeliveryPrice(order.getDeliveryWeight(), order.getDeliveryVolume());
        order.setDeliveryPrice(deliveryCost);
        order = orderRepository.save(order);
        return orderMapper.convertToDto(order);
    }

    public OrderDto assembleOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new NoOrderFoundException("Order not found: " + orderId));
        order.setState(OrderState.ASSEMBLED);
        order = orderRepository.save(order);
        return orderMapper.convertToDto(order);
    }

    public OrderDto handleAssemblyFailure(UUID orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new NoOrderFoundException("Order not found: " + orderId));
        order.setState(OrderState.ASSEMBLY_FAILED);
        order = orderRepository.save(order);
        return orderMapper.convertToDto(order);
    }

    private double calculateDeliveryPrice(Double weight, Double volume) {
        double weightFactor = weight != null ? weight * 5 : 0;
        double volumeFactor = volume != null ? volume * 3 : 0;
        return weightFactor + volumeFactor;
    }
}

