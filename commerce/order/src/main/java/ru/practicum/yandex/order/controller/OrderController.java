package ru.practicum.yandex.order.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.yandex.order.service.OrderService;
import ru.yandex.practicum.interaction.client.OrderClient;
import ru.yandex.practicum.interaction.dto.order.CreateNewOrderRequest;
import ru.yandex.practicum.interaction.dto.order.OrderDto;
import ru.yandex.practicum.interaction.dto.order.ProductReturnRequest;
import ru.yandex.practicum.interaction.exception.NotAuthorizedUserException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController implements OrderClient {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDto>> getClientOrders(@RequestParam String username) {
        if (username == null || username.isBlank()) {
            throw new NotAuthorizedUserException("Имя пользователя не должно быть пустым");
        }
        List<OrderDto> orders = orderService.getOrdersByUsername(username);
        return ResponseEntity.ok(orders);
    }

    @PutMapping
    public ResponseEntity<OrderDto> createNewOrder(@RequestBody CreateNewOrderRequest request) {
        OrderDto order = orderService.createOrder(request);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/return")
    public ResponseEntity<OrderDto> productReturn(@RequestBody ProductReturnRequest request) {
        OrderDto order = orderService.returnProducts(request);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/payment")
    public ResponseEntity<OrderDto> processPayment(@RequestBody UUID orderId) {
        OrderDto order = orderService.processPayment(orderId);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/payment/failed")
    public ResponseEntity<OrderDto> handlePaymentFailure(@RequestBody UUID orderId) {
        OrderDto order = orderService.handlePaymentFailure(orderId);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/delivery")
    public ResponseEntity<OrderDto> processDelivery(@RequestBody UUID orderId) {
        OrderDto order = orderService.processDelivery(orderId);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/delivery/failed")
    public ResponseEntity<OrderDto> handleDeliveryFailure(@RequestBody UUID orderId) {
        OrderDto order = orderService.handleDeliveryFailure(orderId);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/completed")
    public ResponseEntity<OrderDto> completeOrder(@RequestBody UUID orderId) {
        OrderDto order = orderService.completeOrder(orderId);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/calculate/total")
    public ResponseEntity<OrderDto> calculateTotalCost(@RequestBody UUID orderId) {
        OrderDto order = orderService.calculateTotalCost(orderId);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/calculate/delivery")
    public ResponseEntity<OrderDto> calculateDeliveryCost(@RequestBody UUID orderId) {
        OrderDto order = orderService.calculateDeliveryCost(orderId);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/assembly")
    public ResponseEntity<OrderDto> assembleOrder(@RequestBody UUID orderId) {
        OrderDto order = orderService.assembleOrder(orderId);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/assembly/failed")
    public ResponseEntity<OrderDto> handleAssemblyFailure(@RequestBody UUID orderId) {
        OrderDto order = orderService.handleAssemblyFailure(orderId);
        return ResponseEntity.ok(order);
    }
}

