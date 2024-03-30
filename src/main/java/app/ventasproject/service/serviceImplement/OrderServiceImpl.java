package app.ventasproject.service.serviceImplement;

import app.ventasproject.dto.order.OrderDto;
import app.ventasproject.dto.order.OrderMapper;
import app.ventasproject.dto.order.OrderToSaveDto;
import app.ventasproject.exception.NotFoundException;
import app.ventasproject.models.Order;
import app.ventasproject.repositorys.OrderRepository;
import app.ventasproject.service.serviceInterface.OrderService;


import java.time.LocalDateTime;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderMapper orderMapper, OrderRepository orderRepository){
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderDto saveOrder(OrderToSaveDto orderDto) {
        Order order = OrderMapper.INSTANCE.orderToSaveDToToEntity(orderDto);
        Order orderSaved = orderRepository.save(order);
        return orderMapper.orderEntitytoOrderDto(orderSaved);
    }

    @Override
    public OrderDto updateOrder(Long id, OrderToSaveDto order) {
        return null;
    }

    @Override
    public OrderDto searchOrderById(Long id) throws NotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("No encontrado"));
        return orderMapper.orderEntitytoOrderDto(order);
    }

    @Override
    public List<OrderDto> getAllOrder() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> orderMapper.orderEntitytoOrderDto(order))
                .toList();
    }

    @Override
    public List<OrderDto> searchBetweenDates(LocalDateTime date, LocalDateTime date2) {
        return null;
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        orderRepository.delete(order);
    }
}
