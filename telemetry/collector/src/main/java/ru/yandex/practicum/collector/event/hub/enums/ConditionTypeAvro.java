package ru.yandex.practicum.collector.event.hub.enums;

public enum ConditionTypeAvro {
    MOTION,      // Motion detected by motion sensor
    LUMINOSITY,  // Light level detected by light sensor
    SWITCH,      // On/Off state of a switch or socket
    TEMPERATURE, // Temperature reading from temperature sensor
    CO2LEVEL,    // CO2 level from climate sensor
    HUMIDITY     // Humidity level from climate sensor
}

