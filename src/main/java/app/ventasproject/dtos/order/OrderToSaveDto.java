package app.ventasproject.dtos.order;

import app.ventasproject.Enum.StatusEnum;

import java.time.LocalDateTime;

public record OrderToSaveDto(Long id, LocalDateTime orderDate, StatusEnum status) {
}
