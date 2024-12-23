package ru.yandex.practicum.telemetry.serialization.common.event.hub.enums;

public enum ConditionType {
    MOTION,      // Motion detected by motion sensor
    LUMINOSITY,  // Light level detected by light sensor
    SWITCH,      // On/Off state of a switch or socket
    TEMPERATURE, // Temperature reading from temperature sensor
    CO2LEVEL,    // CO2 level from climate sensor
    HUMIDITY     // Humidity level from climate sensor
}

