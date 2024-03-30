package app.ventasproject.service.serviceInterface;

import app.ventasproject.dto.order.OrderDto;
import app.ventasproject.dto.order.OrderToSaveDto;
import app.ventasproject.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    OrderDto saveOrder(OrderToSaveDto order);
    OrderDto updateOrder(Long id, OrderToSaveDto order);
    OrderDto searchOrderById(Long id) throws NotFoundException;
    List<OrderDto> getAllOrder();

    //no se que es el customer

    List<OrderDto> searchBetweenDates(LocalDateTime date, LocalDateTime date2);
    void deleteOrder(Long id);
}
