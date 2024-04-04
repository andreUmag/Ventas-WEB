package app.ventasproject.controllers;

import app.ventasproject.dtos.orderDetail.OrderDetailDto;
import app.ventasproject.dtos.orderDetail.OrderDetailMapper;
import app.ventasproject.dtos.orderDetail.OrderDetailToSaveDto;
import app.ventasproject.models.Order;
import app.ventasproject.models.OrderDetail;
import app.ventasproject.models.Product;
import app.ventasproject.services.serviceInterface.OrderDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.RequestEntity.put;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testcontainers.shaded.org.hamcrest.Matchers.hasSize;
import static org.testcontainers.shaded.org.hamcrest.Matchers.is;


@WebMvcTest(OrderDetailController.class)
@ExtendWith(MockitoExtension.class)
public class OrderDetailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderDetailService orderDetailService;

    @Autowired
    private ObjectMapper objectMapper;

    private Order order;
    private OrderDetail orderDetail;
    private OrderDetailDto orderDetailDto;

    @BeforeEach
    void setUp() {
        order = Order.builder().id(1L).build();

        orderDetail = OrderDetail.builder()
                .id(1L)
                .order(order)
                .build();

        orderDetailDto = OrderDetailMapper.INSTANCE.orderDetailEntitytoOrderDetailDto(orderDetail);
    }

    @Test
    public void saveOrderDetailTest() throws Exception {
        OrderDetailToSaveDto orderDetailToSaveDto = new OrderDetailToSaveDto(orderDetail.getId(),
                orderDetail.getAddressOrder(), orderDetail.getTransporter(), orderDetail.getGuide());

        when(orderDetailService.saveOrderDetail(orderDetailToSaveDto)).thenReturn(orderDetailDto);

        mockMvc.perform(post("/api/v1/shipping")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDetailDto)))
                .andExpect(status().isOk());

        verify(orderDetailService, times(1)).saveOrderDetail(orderDetailToSaveDto);
    }

    @Test
    public void getOrderDetailTest() throws Exception {
        List<OrderDetailDto> orderDetailDtoList = new ArrayList<>();
        orderDetailDtoList.add(orderDetailDto);

        when(orderDetailService.getAllOrderDetail()).thenReturn(orderDetailDtoList);

        mockMvc.perform(get("/api/v1/shipping"))
                .andExpect(status().isOk());


        verify(orderDetailService, times(1)).getAllOrderDetail();
    }

    @Test
    public void getOrderDetailByIdTest() throws Exception {
        when(orderDetailService.searchOrderDetailById(orderDetail.getId())).thenReturn(orderDetailDto);

        mockMvc.perform(get("/api/v1/shipping/{id}", orderDetail.getId()))
                .andExpect(status().isOk());

        verify(orderDetailService, times(1)).searchOrderDetailById(orderDetail.getId());
    }

    @Test
    public void updateOrderDetailTest() throws Exception {
        OrderDetailToSaveDto orderDetailSaveDto = new OrderDetailToSaveDto(orderDetail.getId(),
                orderDetail.getAddressOrder(), orderDetail.getTransporter(), orderDetail.getGuide());

        when(orderDetailService.updateOrderDetail(orderDetail.getId(), orderDetailSaveDto)).thenReturn(orderDetailDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/shipping/{id}", orderDetail.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDetailSaveDto)))
                .andExpect(status().isOk());

        verify(orderDetailService, times(1)).updateOrderDetail(orderDetail.getId(), orderDetailSaveDto);
    }

    @Test
    public void getOrderDetailDtoByTransporterTest() throws Exception {
        List<OrderDetailDto> orderDetailDtoList = new ArrayList<>();
        orderDetailDtoList.add(orderDetailDto);

        when(orderDetailService.serachOrderDetailByTransporter("transporter")).thenReturn(orderDetailDtoList);

        mockMvc.perform(get("/api/v1/shipping/carrier?name=transporter"))
                .andExpect(status().isOk());

        verify(orderDetailService, times(1)).serachOrderDetailByTransporter("transporter");
    }

    @Test
    public void getOrderDetailByOrderIdTest() throws Exception {
        when(orderDetailService.searchOrderDetailByOrderId(1L)).thenReturn(orderDetailDto);

        mockMvc.perform(get("/api/v1/shipping/order/1"))
                .andExpect(status().isOk());

        verify(orderDetailService, times(1)).searchOrderDetailByOrderId(1L);
    }

    @Test
    public void deleteOrderDetailTest() throws Exception {
        doNothing().when(orderDetailService).deleteOrderDetail(orderDetail.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/shipping/{id}", orderDetail.getId()))
                .andExpect(status().isOk());

        verify(orderDetailService, times(1)).deleteOrderDetail(orderDetail.getId());
    }
}
