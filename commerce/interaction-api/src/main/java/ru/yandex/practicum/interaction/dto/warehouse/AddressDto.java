package ru.yandex.practicum.interaction.dto.warehouse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    @NotBlank(message = "Country must not be blank")
    private String country;

    @NotBlank(message = "City must not be blank")
    private String city;

    @NotBlank(message = "Street address must not be blank")
    private String street;

    @NotBlank(message = "House must not be blank")
    private String house;

    @NotBlank(message = "flat must not be blank")
    private String flat;
}

