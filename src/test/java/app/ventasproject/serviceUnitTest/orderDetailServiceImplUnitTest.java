package app.ventasproject.serviceUnitTest;

import app.ventasproject.Enum.StatusEnum;
import app.ventasproject.dtos.client.ClientToSaveDto;
import app.ventasproject.dtos.order.OrderToSaveDto;
import app.ventasproject.dtos.orderDetail.OrderDetailDto;
import app.ventasproject.dtos.orderDetail.OrderDetailMapper;
import app.ventasproject.dtos.orderDetail.OrderDetailToSaveDto;
import app.ventasproject.exceptions.NotFoundException;
import app.ventasproject.models.Order;
import app.ventasproject.models.OrderDetail;
import app.ventasproject.repositories.OrderDetailRepository;
import app.ventasproject.services.serviceImplement.OrderDetailServiceImpl;
import app.ventasproject.services.serviceInterface.OrderDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class orderDetailServiceImplUnitTest {

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Mock
    private OrderDetailMapper orderDetailMapper;

    @InjectMocks
    private OrderDetailServiceImpl orderDetailService;

    private Order order;
    private OrderDetail orderDetail;
    private OrderDetailDto orderDetailDto;
    private OrderDetailToSaveDto orderDetailToSaveDto;

    @BeforeEach
    void setUp() {
        order = Order.builder().id(1L).status(StatusEnum.ENVIADO).build();

        orderDetail = OrderDetail.builder().id(1L).addressOrder("cra 4").guide("21a").transporter("deprisa").order(order).build();

        orderDetailToSaveDto = new OrderDetailToSaveDto(1L, "test", "test1", "123");

        orderDetailDto = new OrderDetailDto(1L,"cra 4" ,"deprisa","21a");
    }

    @Test
    void testSaveOrderDetail() {
        given(orderDetailRepository.save(any())).willReturn(orderDetail);
        when(orderDetailMapper.orderDetailEntitytoOrderDetailDto(orderDetail)).thenReturn(orderDetailDto);

        OrderDetailDto savedOrderDetail = orderDetailService.saveOrderDetail(orderDetailToSaveDto);

        assertEquals(orderDetailDto, savedOrderDetail);
    }

    @Test
    void testUpdateOrderDetail() {
        given(orderDetailRepository.findById(orderDetail.getId())).willReturn(Optional.of(orderDetail));

        when(orderDetailMapper.orderDetailEntitytoOrderDetailDto(any())).thenReturn(orderDetailDto);

        OrderDetailDto updatedOrderDetail = orderDetailService.updateOrderDetail(orderDetail.getId(), orderDetailToSaveDto);

        assertEquals(orderDetailDto, updatedOrderDetail);
    }

    @Test
    void testSearchOrderDetailByIdNotFound() {
        when(orderDetailRepository.findById(orderDetail.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> orderDetailService.searchOrderDetailById(orderDetail.getId()));
    }

    @Test
    void testSearchOrderDetailByIdFound() {
        given(orderDetailRepository.findById(orderDetail.getId())).willReturn(Optional.of(orderDetail));
        when(orderDetailMapper.orderDetailEntitytoOrderDetailDto(orderDetail)).thenReturn(orderDetailDto);

        OrderDetailDto orderDetailFound = orderDetailService.searchOrderDetailById(orderDetail.getId());

        assertEquals(orderDetailDto, orderDetailFound);
    }

    @Test
    void testGetAllOrderDetail() {
        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails.add(orderDetail);

        when(orderDetailRepository.findAll()).thenReturn(orderDetails);
        when(orderDetailMapper.orderDetailEntitytoOrderDetailDto(orderDetail)).thenReturn(orderDetailDto);

        List<OrderDetailDto> result =orderDetailService.getAllOrderDetail();

        assertEquals(orderDetailDto, result.get(0));
    }

    @Test
    void testSearchOrderDetailByOrderIdNotFound() {
        when(orderDetailRepository.findOrderDetailByOrderId(orderDetail.getOrder().getId())).thenReturn(null);

        assertThrows(NotFoundException.class, () -> orderDetailService.searchOrderDetailByOrderId(orderDetail.getOrder().getId()));
    }

    @Test
    void testSearchOrderDetailByOrderIdFound() {
        given(orderDetailRepository.findOrderDetailByOrderId(orderDetail.getOrder().getId())).willReturn(orderDetail);

        when(orderDetailMapper.orderDetailEntitytoOrderDetailDto(orderDetail)).thenReturn(orderDetailDto);

        OrderDetailDto orderDetailFound = orderDetailService.searchOrderDetailByOrderId(orderDetail.getOrder().getId());

        assertEquals(orderDetailDto, orderDetailFound);
    }

    @Test
    void testSerachOrderDetailByTransporter() {
        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails.add(orderDetail);

        given(orderDetailRepository.findOrderDetailByTransporter(orderDetail.getTransporter())).willReturn(orderDetails);
        when(orderDetailMapper.orderDetailEntitytoOrderDetailDto(orderDetail)).thenReturn(orderDetailDto);

        List<OrderDetailDto> result = orderDetailService.serachOrderDetailByTransporter(orderDetail.getTransporter());

        assertEquals(orderDetailDto, result.get(0));
    }

    @Test
    void testRemoveOrderDetail() {
        when(orderDetailRepository.findById(orderDetail.getId())).thenReturn(Optional.of(orderDetail));

        orderDetailService.removeOrderDetail(orderDetail.getId());
    }
}
