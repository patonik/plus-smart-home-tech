package ru.yandex.practicum.infra.analyzer.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.infra.analyzer.entity.Action;
import ru.yandex.practicum.infra.analyzer.entity.Condition;
import ru.yandex.practicum.infra.analyzer.entity.Scenario;
import ru.yandex.practicum.kafka.telemetry.event.ConditionOperationAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScenarioEvaluator {

    /**
     * Evaluates a list of scenarios against the current state of sensor data in the snapshot.
     * @param scenarios List of scenarios to evaluate.
     * @param snapshotEvent Current snapshot of sensor data in SensorsSnapshotAvro format.
     * @return List of actions to execute if conditions are met.
     */
    public List<Action> evaluate(List<Scenario> scenarios, SensorsSnapshotAvro snapshotEvent) {
        return scenarios.stream()
            .filter(scenario -> checkConditions(scenario.getConditions(), snapshotEvent))
            .flatMap(scenario -> scenario.getActions().stream())
            .collect(Collectors.toList());
    }

    /**
     * Checks if all conditions in a scenario are met based on the sensor data snapshot.
     * @param conditions List of conditions to check.
     * @param snapshotEvent Current snapshot of sensor data in SensorsSnapshotAvro format.
     * @return True if all conditions are met, false otherwise.
     */
    private boolean checkConditions(List<Condition> conditions, SensorsSnapshotAvro snapshotEvent) {
        return conditions.stream().allMatch(condition -> checkCondition(condition, snapshotEvent));
    }

    /**
     * Checks if a single condition is met based on the sensor data snapshot.
     * @param condition The condition to check.
     * @param snapshotEvent Current snapshot of sensor data in SensorsSnapshotAvro format.
     * @return True if the condition is met, false otherwise.
     */
    private boolean checkCondition(Condition condition, SensorsSnapshotAvro snapshotEvent) {
        Object sensorValue = snapshotEvent.getSensorsState().get(condition.getSensorId());
        return sensorValue != null && evaluateCondition(sensorValue, condition);
    }

    /**
     * Evaluates the condition operation based on the sensor data value and condition value.
     * @param sensorValue The current value from the sensor data.
     * @param condition The condition to evaluate.
     * @return True if the condition is satisfied, false otherwise.
     */
    private boolean evaluateCondition(Object sensorValue, Condition condition) {
        if (sensorValue instanceof Number && condition.getValue() != null) {
            double sensorValueDouble = ((Number) sensorValue).doubleValue();
            double conditionValueDouble = ((Number) condition.getValue()).doubleValue();

            return switch (condition.getOperation()) {
                case EQUALS -> sensorValueDouble == conditionValueDouble;
                case GREATER_THAN -> sensorValueDouble > conditionValueDouble;
                case LOWER_THAN -> sensorValueDouble < conditionValueDouble;
            };
        }

        if (condition.getOperation() == ConditionOperationAvro.EQUALS) {
            return sensorValue.equals(condition.getValue());
        }

        throw new IllegalArgumentException("Unsupported or mismatched operation for values: " + condition.getOperation());
    }
}
