package ru.yandex.practicum.interaction.dto.warehouse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DimensionDto {

    @NotNull(message = "Width is required")
    @Min(value = 1, message = "Width must be at least 1")
    private Double width;

    @NotNull(message = "Height is required")
    @Min(value = 1, message = "Height must be at least 1")
    private Double height;

    @NotNull(message = "Depth is required")
    @Min(value = 1, message = "Depth must be at least 1")
    private Double depth;
}

