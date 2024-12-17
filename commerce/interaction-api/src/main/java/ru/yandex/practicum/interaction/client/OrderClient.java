package ru.yandex.practicum.interaction.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.interaction.dto.order.CreateNewOrderRequest;
import ru.yandex.practicum.interaction.dto.order.OrderDto;
import ru.yandex.practicum.interaction.dto.order.ProductReturnRequest;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "order-service", path = "/api/v1/order")
public interface OrderClient {

    @GetMapping("/api/v1/order")
    ResponseEntity<List<OrderDto>> getClientOrders(@RequestParam("username") String username);

    @PutMapping("/api/v1/order")
    ResponseEntity<OrderDto> createNewOrder(@RequestBody CreateNewOrderRequest request);

    @PostMapping("/api/v1/order/return")
    ResponseEntity<OrderDto> productReturn(@RequestBody ProductReturnRequest request);

    @PostMapping("/api/v1/order/payment")
    ResponseEntity<OrderDto> processPayment(@RequestBody UUID orderId);

    @PostMapping("/api/v1/order/payment/failed")
    ResponseEntity<OrderDto> handlePaymentFailure(@RequestBody UUID orderId);

    @PostMapping("/api/v1/order/delivery")
    ResponseEntity<OrderDto> processDelivery(@RequestBody UUID orderId);

    @PostMapping("/api/v1/order/delivery/failed")
    ResponseEntity<OrderDto> handleDeliveryFailure(@RequestBody UUID orderId);

    @PostMapping("/api/v1/order/completed")
    ResponseEntity<OrderDto> completeOrder(@RequestBody UUID orderId);

    @PostMapping("/api/v1/order/calculate/total")
    ResponseEntity<OrderDto> calculateTotalCost(@RequestBody UUID orderId);

    @PostMapping("/api/v1/order/calculate/delivery")
    ResponseEntity<OrderDto> calculateDeliveryCost(@RequestBody UUID orderId);

    @PostMapping("/api/v1/order/assembly")
    ResponseEntity<OrderDto> assembleOrder(@RequestBody UUID orderId);

    @PostMapping("/api/v1/order/assembly/failed")
    ResponseEntity<OrderDto> handleAssemblyFailure(@RequestBody UUID orderId);
}

