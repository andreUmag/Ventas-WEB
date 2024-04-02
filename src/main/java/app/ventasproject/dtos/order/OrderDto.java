package app.ventasproject.dtos.order;
import app.ventasproject.Enum.StatusEnum;
import app.ventasproject.dtos.orderDetail.OrderDetailDto;
import app.ventasproject.dtos.orderItem.OrderItemDto;
import app.ventasproject.models.Payment;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public record OrderDto(Long id, LocalDateTime orderDate, StatusEnum status, Payment payment, List<OrderDetailDto> orderDetails,
                       List<OrderItemDto> orderItems) {

    public List<OrderDetailDto> getOrderDetails(){
        return Collections.unmodifiableList(orderDetails);
    }
    public List<OrderItemDto> getOrderItems(){
        return Collections.unmodifiableList(orderItems);
    }
}
