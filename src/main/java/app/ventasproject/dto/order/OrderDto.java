package app.ventasproject.dto.order;
import app.ventasproject.Enum.StatusEnum;
import app.ventasproject.dto.orderDetail.OrderDetailDto;
import app.ventasproject.dto.orderItem.OrderItemDto;
import app.ventasproject.dto.payment.PaymentDto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public record OrderDto(Long id, LocalDateTime orderDate, StatusEnum status,
                       List<PaymentDto> payments, List<OrderDetailDto> orderDetails,
                       List<OrderItemDto> orderItems) {
    public List<PaymentDto> getPayments(){
        return Collections.unmodifiableList(payments);
    }
    public List<OrderDetailDto> getOrderDetails(){
        return Collections.unmodifiableList(orderDetails);
    }
    public List<OrderItemDto> getOrderItems(){
        return Collections.unmodifiableList(orderItems);
    }
}
