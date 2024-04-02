package app.ventasproject.services.serviceInterface;

import app.ventasproject.dtos.orderItem.OrderItemDto;
import app.ventasproject.dtos.orderItem.OrderItemToSaveDto;

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
