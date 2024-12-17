package ru.practicum.yandex.payment.service;


import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.practicum.yandex.payment.exception.NoPaymentFoundException;
import ru.practicum.yandex.payment.exception.NotEnoughInfoInOrderToCalculateException;
import ru.practicum.yandex.payment.repository.PaymentRepository;
import ru.yandex.practicum.interaction.client.OrderClient;
import ru.yandex.practicum.interaction.client.ProductClient;
import ru.yandex.practicum.interaction.domain.Payment;
import ru.yandex.practicum.interaction.dto.order.OrderDto;
import ru.yandex.practicum.interaction.dto.pay.PaymentDto;
import ru.yandex.practicum.interaction.dto.product.ProductDto;
import ru.yandex.practicum.interaction.enums.PayState;
import ru.yandex.practicum.interaction.mapper.PaymentMapper;

import java.util.Map;
import java.util.UUID;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final ProductClient productClient;
    private final OrderClient orderClient;
    private final double VAT;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper, ProductClient productClient,
                          OrderClient orderClient, @Value("${VAT}") double VAT) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.productClient = productClient;
        this.orderClient = orderClient;
        this.VAT = VAT;
    }

    public PaymentDto createPayment(OrderDto orderDto) {
        Double deliveryPrice = orderDto.getDeliveryPrice();
        Double productPrice = orderDto.getProductPrice();
        Double totalPrice = orderDto.getTotalPrice();
        if (deliveryPrice == null || productPrice == null) {
            throw new NotEnoughInfoInOrderToCalculateException("No delivery price or product price");
        }
        Payment payment = new Payment();
        payment.setOrderId(orderDto.getOrderId());
        payment.setProductTotal(productPrice);
        payment.setFeeTotal(productPrice * VAT);
        payment.setDeliveryTotal(deliveryPrice);
        payment.setTotalPayment(totalPrice);
        payment.setState(PayState.PENDING);
        payment = paymentRepository.save(payment);
        return paymentMapper.convertToDto(payment);
    }


    public double calculateTotalCost(OrderDto orderDto) {
        double productCost = calculateProductCost(orderDto);
        return productCost + productCost * VAT + orderDto.getDeliveryPrice();
    }


    public void refundPayment(UUID orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
            .orElseThrow(() -> new NoPaymentFoundException("Payment not found"));
        if (payment.getState().equals(PayState.PENDING)) {
            payment.setState(PayState.SUCCESS);
        }
        try {
            OrderDto orderDto = orderClient.processPayment(orderId).getBody();
            if (orderDto == null) {
                throw new RuntimeException("Order not found");
            }
        } catch (FeignException.NotFound e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    public double calculateProductCost(OrderDto orderDto) throws NotEnoughInfoInOrderToCalculateException {
        Map<UUID, Integer> products = orderDto.getProducts();
        if (products == null || products.isEmpty()) {
            throw new NotEnoughInfoInOrderToCalculateException("No products found");
        }
        double productPrice = 0;
        for (UUID uuid : products.keySet()) {
            try {
                ProductDto productDto = productClient.getProductById(uuid).getBody();
                if (productDto == null) {
                    throw new RuntimeException("Product not found in store");
                }
                productPrice += productDto.getPrice() * products.get(uuid);
            } catch (FeignException.NotFound e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return productPrice;
    }


    public void simulatePaymentFailure(UUID orderId) throws NoPaymentFoundException {
        Payment payment = paymentRepository.findByOrderId(orderId)
            .orElseThrow(() -> new NoPaymentFoundException("Payment not found"));
        if (payment.getState().equals(PayState.PENDING)) {
            payment.setState(PayState.FAILED);
        }
        try {
            OrderDto orderDto = orderClient.handlePaymentFailure(orderId).getBody();
            if (orderDto == null) {
                throw new RuntimeException("Order not found");
            }
        } catch (FeignException.NotFound e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

