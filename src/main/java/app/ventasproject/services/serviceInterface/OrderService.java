package app.ventasproject.services.serviceInterface;

import app.ventasproject.dtos.order.OrderDto;
import app.ventasproject.dtos.order.OrderToSaveDto;
import app.ventasproject.exceptions.NotFoundException;

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