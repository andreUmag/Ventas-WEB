package app.ventasproject.services.serviceImplement;

import app.ventasproject.dtos.orderDetail.OrderDetailDto;
import app.ventasproject.dtos.orderDetail.OrderDetailMapper;
import app.ventasproject.dtos.orderDetail.OrderDetailToSaveDto;
import app.ventasproject.exceptions.NotFoundException;
import app.ventasproject.models.Order;
import app.ventasproject.models.OrderDetail;
import app.ventasproject.repositories.OrderDetailRepository;
import app.ventasproject.services.serviceInterface.OrderDetailService;

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
    public OrderDetailDto updateOrderDetail(Long id, OrderDetailToSaveDto orderDetailDto) {
        return orderDetailRepository.findById(id)
                .map(orderDetail -> {
                    orderDetail.setAddressOrder(orderDetailDto.addressOrder());
                    orderDetail.setTransporter(orderDetailDto.transporter());
                    orderDetail.setGuide(orderDetailDto.guide());

                    OrderDetail orderDetailSave = orderDetailRepository.save(orderDetail);

                    return orderDetailMapper.orderDetailEntitytoOrderDetailDto(orderDetailSave);
                }).orElseThrow(() -> new NotFoundException("No encontrado."));
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
    public OrderDetailDto searchOrderDetailByOrderId(Long orderId) {
        OrderDetail orderDetail = orderDetailRepository.findOrderDetailByOrderId(orderId);
        if(Objects.isNull(orderDetail))
            throw new NotFoundException("No encontrado");
        return OrderDetailMapper.INSTANCE.orderDetailEntitytoOrderDetailDto(orderDetail);
    }

    @Override
    public List<OrderDetailDto> serachOrderDetailByTransporter(String transporter) {
        return  orderDetailRepository.findOrderDetailByTransporter(transporter)
                .stream()
                .map(detalleEnvio -> orderDetailMapper.orderDetailEntitytoOrderDetailDto(detalleEnvio))
                .toList();
    }

    @Override
    public void removeOrderDetail(Long id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id).orElseThrow();
        orderDetailRepository.delete(orderDetail);
    }
}
