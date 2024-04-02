package app.ventasproject.services.serviceInterface;
import app.ventasproject.dtos.orderDetail.OrderDetailDto;
import app.ventasproject.dtos.orderDetail.OrderDetailToSaveDto;
import app.ventasproject.exceptions.NotFoundException;
import app.ventasproject.models.Order;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface OrderDetailService {
    OrderDetailDto saveOrderDetail(OrderDetailToSaveDto orderDetail);
    OrderDetailDto updateOrderDetail(Long id, OrderDetailToSaveDto orderDetail);
    OrderDetailDto searchOrderDetailById(Long id) throws NotFoundException;
    List<OrderDetailDto> getAllOrderDetail();
    OrderDetailDto searchOrderDetailByOrderId(Long orderId);
    List<OrderDetailDto> serachOrderDetailByTransporter(String transporter);
    void deleteOrderDetail(Long id);
}
