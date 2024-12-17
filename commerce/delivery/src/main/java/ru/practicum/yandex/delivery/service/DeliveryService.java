package ru.practicum.yandex.delivery.service;


import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.yandex.delivery.exception.NoDeliveryFoundException;
import ru.practicum.yandex.delivery.repository.DeliveryRepository;
import ru.yandex.practicum.interaction.client.OrderClient;
import ru.yandex.practicum.interaction.client.WarehouseClient;
import ru.yandex.practicum.interaction.domain.Delivery;
import ru.yandex.practicum.interaction.dto.delivery.DeliveryDto;
import ru.yandex.practicum.interaction.dto.order.OrderDto;
import ru.yandex.practicum.interaction.dto.warehouse.AssemblyProductsForOrderRequest;
import ru.yandex.practicum.interaction.enums.DeliveryState;
import ru.yandex.practicum.interaction.mapper.DeliveryMapper;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final DeliveryMapper deliveryMapper;
    private final WarehouseClient warehouseClient;
    private final OrderClient orderClient;

    public DeliveryDto planDelivery(DeliveryDto deliveryDto) {
        Delivery delivery = deliveryMapper.convertToEntity(deliveryDto);
        delivery.setDeliveryState(DeliveryState.IN_PROGRESS);
        delivery = deliveryRepository.save(delivery);
        OrderDto orderDto;
        try {
            orderDto = orderClient.assembleOrder(delivery.getOrderId()).getBody();
            if (orderDto == null) {
                throw new RuntimeException("Order not found");
            }
        } catch (FeignException.NotFound e) {
            throw new RuntimeException(e.getMessage());
        }
        try {
            AssemblyProductsForOrderRequest request = new AssemblyProductsForOrderRequest();
            request.setOrderId(delivery.getOrderId());
            request.setProducts(orderDto.getProducts());
            boolean success = warehouseClient.assembleProducts(request).getStatusCode().is2xxSuccessful();
            if (!success) {
                throw new RuntimeException("Failure at assembling products in warehouse");
            }
        } catch (FeignException.NotFound e) {
            throw new RuntimeException(e.getMessage());
        }
        return deliveryMapper.convertToDto(delivery);
    }

    public void deliverySuccessful(UUID orderId) {
        Delivery delivery = findDeliveryByOrderId(orderId);
        delivery.setDeliveryState(DeliveryState.DELIVERED);
    }


    public void deliveryPicked(UUID orderId) {
        Delivery delivery = findDeliveryByOrderId(orderId);
        delivery.setDeliveryState(DeliveryState.IN_PROGRESS);
    }


    public void deliveryFailed(UUID orderId) {
        Delivery delivery = findDeliveryByOrderId(orderId);
        delivery.setDeliveryState(DeliveryState.FAILED);
    }


    public Double deliveryCost(OrderDto orderDto) {
        Delivery delivery = findDeliveryByOrderId(orderDto.getOrderId());

        double baseCost = 5.0; // Base delivery fee
        double weightFactor = 0.3; // Cost per weight unit
        double volumeFactor = 0.2; // Cost per volume unit
        String fromStreet = delivery.getFromAddress().getStreet();
        double cost = baseCost * (fromStreet.contains("ADDRESS_2") ? 2 : 1);
        cost *= (orderDto.getFragile() ? 1.2 : 1);
        cost += orderDto.getDeliveryWeight() * weightFactor;
        cost += orderDto.getDeliveryVolume() * volumeFactor;
        cost *= fromStreet.equals(delivery.getToAddress().getStreet()) ? 1 : 1.2;
        return cost;
    }


    private Delivery findDeliveryByOrderId(UUID orderId) {
        return deliveryRepository.findByOrderId(orderId)
            .orElseThrow(() -> new NoDeliveryFoundException("Delivery not found for order ID: " + orderId));
    }
}

