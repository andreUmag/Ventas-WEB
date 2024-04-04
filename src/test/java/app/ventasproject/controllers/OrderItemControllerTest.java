package app.ventasproject.controllers;

import app.ventasproject.dtos.orderDetail.OrderDetailDto;
import app.ventasproject.dtos.orderDetail.OrderDetailMapper;
import app.ventasproject.dtos.orderDetail.OrderDetailToSaveDto;
import app.ventasproject.dtos.orderItem.OrderItemDto;
import app.ventasproject.dtos.orderItem.OrderItemMapper;
import app.ventasproject.dtos.orderItem.OrderItemToSaveDto;
import app.ventasproject.models.Order;
import app.ventasproject.models.OrderDetail;
import app.ventasproject.models.OrderItem;
import app.ventasproject.services.serviceInterface.OrderItemService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderItemsController.class)
@ExtendWith(MockitoExtension.class)
public class OrderItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderItemService orderItemService;

    @Autowired
    private ObjectMapper objectMapper;

    private Order order;
    private OrderItem orderItem;
    private OrderItemDto orderItemDto;

    @BeforeEach
    void setUp() {
        order = Order.builder().id(1L).build();

        orderItem = OrderItem.builder()
                .id(1L)
                .order(order)
                .build();

        orderItemDto = OrderItemMapper.INSTANCE.orderItemEntitytoOrderItemDto(orderItem);
    }

    @Test
    public void saveOrderItemTest() throws Exception {
        OrderItemToSaveDto orderItemToSaveDto = new OrderItemToSaveDto(orderItem.getId(),
                orderItem.getQuantity(), orderItem.getUnitPrice());

        when(orderItemService.saveOrderItem(orderItemToSaveDto)).thenReturn(orderItemDto);

        mockMvc.perform(post("/api/v1/order-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderItemDto)))
                .andExpect(status().isOk());

        verify(orderItemService, times(1)).saveOrderItem(orderItemToSaveDto);
    }

    @Test
    public void getOrderItemTest() throws Exception {
        List<OrderItemDto> orderItemDtoList = new ArrayList<>();
        orderItemDtoList.add(orderItemDto);

        when(orderItemService.getAllOrdenItem()).thenReturn(orderItemDtoList);

        mockMvc.perform(get("/api/v1/order-items"))
                .andExpect(status().isOk());

        verify(orderItemService, times(1)).getAllOrdenItem();
    }

    @Test
    public void getOrderItemByIdTest() throws Exception {
        when(orderItemService.searchOrderItemById(orderItem.getId())).thenReturn(orderItemDto);

        mockMvc.perform(get("/api/v1/order-items/{id}", orderItem.getId()))
                .andExpect(status().isOk());

        verify(orderItemService, times(1)).searchOrderItemById(orderItem.getId());
    }

    @Test
    public void updateOrderItemTest() throws Exception {
        OrderItemToSaveDto orderItemToSaveDto = new OrderItemToSaveDto(orderItem.getId(),
                orderItem.getQuantity(), orderItem.getUnitPrice());

        when(orderItemService.updateOrderItem(orderItem.getId(), orderItemToSaveDto)).thenReturn(orderItemDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/order-items/{id}", orderItem.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderItemToSaveDto)))
                .andExpect(status().isOk());

        verify(orderItemService, times(1)).updateOrderItem(orderItem.getId(), orderItemToSaveDto);
    }

    @Test
    public void getOrderDetailByOrderIdTest() throws Exception {
        List<OrderItemDto> orderItemDtoList = new ArrayList<>();
        orderItemDtoList.add(orderItemDto);

        when(orderItemService.searchOrderItemByOrderId(1L)).thenReturn(orderItemDtoList);

        mockMvc.perform(get("/api/v1/order-items/order/1"))
                .andExpect(status().isOk());

        verify(orderItemService, times(1)).searchOrderItemByOrderId(1L);
    }

    @Test
    public void deleteOrderDetailTest() throws Exception {
        doNothing().when(orderItemService).deleteOrderItem(orderItem.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/order-items/{id}", orderItem.getId()))
                .andExpect(status().isOk());

        verify(orderItemService, times(1)).deleteOrderItem(orderItem.getId());
    }
}
