package app.ventasproject.services.serviceInterface;
import app.ventasproject.dtos.orderDetail.OrderDetailDto;
import app.ventasproject.dtos.orderDetail.OrderDetailToSaveDto;
import app.ventasproject.exceptions.NotFoundException;
import app.ventasproject.models.Order;

import java.util.List;

public interface OrderDetailService {
    OrderDetailDto saveOrderDetail(OrderDetailToSaveDto orderDetail);
    OrderDetailDto updateOrderDetail(Long id, OrderDetailToSaveDto orderDetail);
    OrderDetailDto searchOrderDetailById(Long id) throws NotFoundException;
    List<OrderDetailDto> getAllOrderDetail();
    OrderDetailDto searchOrderDetailByOrderId(Order orderId);
    OrderDetailDto serachOrderDetailByTransporter(String transporter);
    void removeOrderDetail(Long id);
}
