package ru.practicum.yandex.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.interaction.domain.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}