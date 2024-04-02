package app.ventasproject.service.serviceInterface;

import app.ventasproject.dto.orderItem.OrderItemDto;
import app.ventasproject.dto.orderItem.OrderItemToSaveDto;
import app.ventasproject.models.Order;

import java.util.List;

public interface OrderItemService {
    OrderItemDto saveOrderItem(OrderItemToSaveDto orderItem);
    OrderItemDto updateOrderItem(Long id, OrderItemToSaveDto orderItem);
    OrderItemDto searchOrderItemById(Long id);
    List<OrderItemDto> getAllOrdenItem();
    List<OrderItemDto> searchOrderItemByOrderId(Long id);
    List<OrderItemDto> searchOrderItemByProductId(Long id);
    void removeOrderItem(Long Id);
}
