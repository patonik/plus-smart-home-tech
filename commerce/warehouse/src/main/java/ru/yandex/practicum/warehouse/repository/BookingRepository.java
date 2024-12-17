package ru.yandex.practicum.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.interaction.domain.OrderBooking;

import java.util.Optional;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<OrderBooking, UUID> {
    Optional<OrderBooking> findByOrderId(UUID id);
}
