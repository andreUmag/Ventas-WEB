package app.ventasproject.dto.payment;

import app.ventasproject.Enum.MethodsEnum;

import java.time.LocalDateTime;

public record PaymentDto(Long id, Float totalPayment, LocalDateTime datePayment, MethodsEnum method) {
}
