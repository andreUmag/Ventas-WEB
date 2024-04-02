package app.ventasproject.services.serviceInterface;

import app.ventasproject.dtos.orderItem.OrderItemDto;
import app.ventasproject.dtos.orderItem.OrderItemToSaveDto;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface OrderItemService {
    OrderItemDto saveOrderItem(OrderItemToSaveDto orderItem);
    OrderItemDto updateOrderItem(Long id, OrderItemToSaveDto orderItem);
    OrderItemDto searchOrderItemById(Long id);
    List<OrderItemDto> getAllOrdenItem();
    List<OrderItemDto> searchOrderItemByOrderId(Long id);
    List<OrderItemDto> searchOrderItemByProductId(Long id);
    void deleteOrderItem(Long Id);
}
