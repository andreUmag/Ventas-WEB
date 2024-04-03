package app.ventasproject.services.serviceImplement;

import app.ventasproject.dtos.order.OrderDto;
import app.ventasproject.dtos.order.OrderMapper;
import app.ventasproject.dtos.order.OrderToSaveDto;
import app.ventasproject.exceptions.NotFoundException;
import app.ventasproject.models.Order;
import app.ventasproject.repositories.OrderRepository;
import app.ventasproject.services.serviceInterface.OrderService;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderMapper orderMapper, OrderRepository orderRepository){
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderDto saveOrder(OrderToSaveDto orderDto) {
        Order order = orderMapper.orderToSaveDToToEntity(orderDto);
        Order orderSaved = orderRepository.save(order);
        return orderMapper.orderEntitytoOrderDto(orderSaved);
    }

    @Override
    public OrderDto updateOrder(Long id, OrderToSaveDto orderDto) {
        return orderRepository.findById(id)
                .map(order->{
                            order.setOrderDate(orderDto.orderDate());
                            order.setStatus(orderDto.status());
                            Order orderSaved = orderRepository.save(order);

                            return orderMapper.orderEntitytoOrderDto(orderSaved);
                        }
                ).orElseThrow(() -> new NotFoundException("Pedido no encontrado."));
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
    public List<OrderDto> searchByClientId(Long id) {
        List<Order> orders = orderRepository.findOrdersByClientId(id);
        return orders.stream()
                .map(order -> orderMapper.orderEntitytoOrderDto(order))
                .toList();
    }

    @Override
    public List<OrderDto> searchBetweenDates(LocalDateTime date, LocalDateTime date2) {
        List<Order> orders = orderRepository.FindIntoDate(date, date2);
        if (orders == null) {
            return new ArrayList<>();
        }
        return orders.stream()
                .map(order -> orderMapper.orderEntitytoOrderDto(order)).collect(Collectors.toList());

    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(()-> new NotFoundException("No encontrado"));
        orderRepository.delete(order);
    }
}
