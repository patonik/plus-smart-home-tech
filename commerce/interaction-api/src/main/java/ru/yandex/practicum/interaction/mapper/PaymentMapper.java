package ru.yandex.practicum.interaction.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.interaction.domain.Payment;
import ru.yandex.practicum.interaction.dto.pay.PaymentDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {

    PaymentDto convertToDto(Payment entity);

    Payment convertToEntity(PaymentDto dto);

}
