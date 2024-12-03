package ru.yandex.practicum.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.interaction.domain.WarehouseProduct;

import java.util.List;
import java.util.UUID;

public interface WarehouseRepository extends JpaRepository<WarehouseProduct, UUID> {
    List<WarehouseProduct> findAllWhereProductIdIn(List<UUID> productIds);
}
