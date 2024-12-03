package ru.yandex.practicum.infra.analyzer.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.infra.analyzer.entity.Device;

import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, String> {

    /**
     * Finds a device by its unique identifier.
     *
     * @param id The unique ID of the device.
     * @return An Optional containing the device if found, or empty if not.
     */
    Optional<Device> findById(String id);

    /**
     * Checks if a device with the specified ID exists in the database.
     *
     * @param id The unique ID of the device.
     * @return True if a device with the given ID exists, false otherwise.
     */
    boolean existsById(String id);

    /**
     * Deletes a device by its unique identifier.
     *
     * @param id The unique ID of the device to delete.
     */
    void deleteById(String id);
}

