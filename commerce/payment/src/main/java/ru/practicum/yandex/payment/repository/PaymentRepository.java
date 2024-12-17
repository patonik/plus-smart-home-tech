package ru.practicum.yandex.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.interaction.domain.Payment;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    Optional<Payment> findByOrderId(UUID orderId);
}
