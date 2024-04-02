package app.ventasproject.service.serviceImplement;

import app.ventasproject.dto.orderItem.OrderItemDto;
import app.ventasproject.dto.orderItem.OrderItemMapper;
import app.ventasproject.dto.orderItem.OrderItemToSaveDto;
import app.ventasproject.models.OrderItem;
import app.ventasproject.repositorys.OrderItemRepository;
import app.ventasproject.service.serviceInterface.OrderItemService;

import java.util.List;

public class OrderItemServiceImpl implements OrderItemService {
    private  final OrderItemMapper orderItemMapper;
    private final OrderItemRepository orderItemRepository;

    public OrderItemServiceImpl(OrderItemMapper orderItemMapper, OrderItemRepository orderItemRepository) {
        this.orderItemMapper = orderItemMapper;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderItemDto saveOrderItem(OrderItemToSaveDto orderItemDto) {
        OrderItem orderItem = orderItemMapper.orderItemToSaveDToToEntity(orderItemDto);
        OrderItem orderItemSave = orderItemRepository.save(orderItem);
        return orderItemMapper.orderItemEntitytoOrderItemDto(orderItemSave);
    }

    @Override
    public OrderItemDto updateOrderItem(Long id, OrderItemToSaveDto orderItem) {
        return null;
    }

    @Override
    public OrderItemDto searchOrderItemById(Long id) {//agregar Not found exception
        OrderItem orderItem = orderItemRepository.findById(id).orElseThrow();
        return orderItemMapper.orderItemEntitytoOrderItemDto(orderItem);
    }

    @Override
    public List<OrderItemDto> getAllOrdenItem() {
        List<OrderItem> orderItem = orderItemRepository.findAll();
        return orderItem.stream()
                .map(orderItems -> orderItemMapper.orderItemEntitytoOrderItemDto(orderItems))
                .toList();
    }

    @Override
    public List<OrderItemDto> searchOrderItemByOrderId(Long id) {
        return null;
    }

    @Override
    public List<OrderItemDto> searchOrderItemByProductId(Long id) {
        return null;
    }

    @Override
    public void removeOrderItem(Long Id) {
        OrderItem orderItem = orderItemRepository.findById(Id).orElseThrow();
        orderItemRepository.delete(orderItem);
    }
}
