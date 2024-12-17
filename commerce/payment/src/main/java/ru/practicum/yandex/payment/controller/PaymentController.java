package ru.practicum.yandex.payment.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.yandex.payment.service.PaymentService;
import ru.yandex.practicum.interaction.dto.order.OrderDto;
import ru.yandex.practicum.interaction.dto.pay.PaymentDto;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Validated
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentDto> createPayment(@RequestBody @NotNull OrderDto orderDto) {
        PaymentDto payment = paymentService.createPayment(orderDto);
        return ResponseEntity.ok(payment);
    }

    @PostMapping("/totalCost")
    public ResponseEntity<Double> getTotalCost(@RequestBody @NotNull OrderDto orderDto) {
        double totalCost = paymentService.calculateTotalCost(orderDto);
        return ResponseEntity.ok(totalCost);
    }

    @PostMapping("/refund")
    public ResponseEntity<Void> refundPayment(@RequestBody UUID orderId) {
        paymentService.refundPayment(orderId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/productCost")
    public ResponseEntity<Double> calculateProductCost(@RequestBody OrderDto orderDto) {
        double productCost = paymentService.calculateProductCost(orderDto);
        return ResponseEntity.ok(productCost);
    }

    @PostMapping("/failed")
    public ResponseEntity<Void> simulatePaymentFailure(@RequestBody UUID orderId) {
        paymentService.simulatePaymentFailure(orderId);
        return ResponseEntity.ok().build();
    }
}


