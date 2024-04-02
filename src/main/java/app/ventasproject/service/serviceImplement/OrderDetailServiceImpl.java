package app.ventasproject.service.serviceImplement;

import app.ventasproject.dto.orderDetail.OrderDetailDto;
import app.ventasproject.dto.orderDetail.OrderDetailMapper;
import app.ventasproject.dto.orderDetail.OrderDetailToSaveDto;
import app.ventasproject.exception.NotFoundException;
import app.ventasproject.models.Order;
import app.ventasproject.models.OrderDetail;
import app.ventasproject.repositorys.OrderDetailRepository;
import app.ventasproject.service.serviceInterface.OrderDetailService;

import java.util.List;
import java.util.Objects;

public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailMapper orderDetailMapper;
    private final OrderDetailRepository orderDetailRepository;

    public OrderDetailServiceImpl(OrderDetailMapper orderDetailMapper, OrderDetailRepository orderDetailRepository) {
        this.orderDetailMapper = orderDetailMapper;
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public OrderDetailDto saveOrderDetail(OrderDetailToSaveDto orderDetailDto) {
        OrderDetail orderDetail = orderDetailMapper.orderDetailToSaveDToToEntity(orderDetailDto);
        OrderDetail orderDetailSaved = orderDetailRepository.save(orderDetail);
        return orderDetailMapper.orderDetailEntitytoOrderDetailDto(orderDetailSaved);
    }

    @Override
    public OrderDetailDto updateOrderDetail(Long id, OrderDetailToSaveDto orderDetail) {
        return null;
    }

    @Override
    public OrderDetailDto searchOrderDetailById(Long id) throws NotFoundException {
        OrderDetail orderDetail = orderDetailRepository.findById(id).orElseThrow(()-> new NotFoundException("No encontrado"));
        return orderDetailMapper.orderDetailEntitytoOrderDetailDto(orderDetail);
    }

    @Override
    public List<OrderDetailDto> getAllOrderDetail() {
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();
        return orderDetails.stream()
                .map(orderDetail -> orderDetailMapper.orderDetailEntitytoOrderDetailDto(orderDetail))
                .toList();
    }

    @Override
    public OrderDetailDto searchOrderDetailByOrderId(Order orderId) {
        OrderDetail orderDetail = orderDetailRepository.findOrderDetailByOrder(orderId);
        if(Objects.isNull(orderDetail))
            throw new NotFoundException("No encontrado");
        return OrderDetailMapper.INSTANCE.orderDetailEntitytoOrderDetailDto(orderDetail);
    }

    @Override
    public OrderDetailDto serachOrderDetailByTransporter(String transporter) {
        OrderDetail orderDetail = orderDetailRepository.findOrderDetailByTransporter(transporter);
        if(Objects.isNull(orderDetail))
            throw new NotFoundException("No encontrado");
        return OrderDetailMapper.INSTANCE.orderDetailEntitytoOrderDetailDto(orderDetail);
    }

    @Override
    public void removeOrderDetail(Long id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id).orElseThrow();
        orderDetailRepository.delete(orderDetail);
    }
}
