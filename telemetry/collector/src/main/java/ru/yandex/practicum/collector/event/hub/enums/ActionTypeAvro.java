package ru.yandex.practicum.collector.event.hub.enums;

public enum ActionTypeAvro {
    ACTIVATE,    // Activate a device (turn on)
    DEACTIVATE,  // Deactivate a device (turn off)
    INVERSE,     // Invert current device state
    SET_VALUE    // Set a specific value (e.g., brightness level)
}

