package app.ventasproject.dto.order;
import app.ventasproject.Enum.StatusEnum;
import app.ventasproject.dto.orderDetail.OrderDetailDto;
import app.ventasproject.dto.orderItem.OrderItemDto;
import app.ventasproject.dto.payment.PaymentDto;
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
