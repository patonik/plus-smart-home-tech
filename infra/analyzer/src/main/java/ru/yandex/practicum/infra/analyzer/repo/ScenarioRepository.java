package ru.yandex.practicum.infra.analyzer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.infra.analyzer.entity.Scenario;

import java.util.List;

@Repository
public interface ScenarioRepository extends JpaRepository<Scenario, Long> {

    /**
     * Finds all scenarios associated with a specific hub.
     *
     * @param hubId The ID of the hub.
     * @return A list of scenarios associated with the specified hub.
     */
    List<Scenario> findByHubId(String hubId);

    void deleteByName(String scenarioName);
}

