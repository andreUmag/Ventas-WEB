package app.ventasproject.serviceUnitTest;

import app.ventasproject.Enum.StatusEnum;
import app.ventasproject.dtos.order.OrderDto;
import app.ventasproject.dtos.order.OrderMapper;
import app.ventasproject.dtos.order.OrderToSaveDto;
import app.ventasproject.exceptions.NotFoundException;
import app.ventasproject.models.Client;
import app.ventasproject.models.Order;
import app.ventasproject.models.OrderItem;
import app.ventasproject.models.Product;
import app.ventasproject.repositories.OrderItemRepository;
import app.ventasproject.repositories.OrderRepository;
import app.ventasproject.services.serviceImplement.OrderItemServiceImpl;
import app.ventasproject.services.serviceImplement.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class orderServiceImplUnitTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order, order2, order3;
    private Client client;
    private OrderDto orderDto;
    private OrderToSaveDto orderToSaveDto;

    @BeforeEach
    void setUp(){
        client = Client.builder().id(1L).name("len").build();

        order = Order.builder().id(1L).status(StatusEnum.ENVIADO).client(client).orderDate(LocalDateTime.now().minusDays(15)).build();

        orderToSaveDto = new OrderToSaveDto(order.getId(),order.getOrderDate(),order.getStatus());

        orderDto = OrderMapper.INSTANCE.orderEntitytoOrderDto(order);
    }

    @Test
    void givenOrder_whenSaveOrder_thenReturnSavedOrder() {
        given(orderRepository.save(any())).willReturn(order);

        when(orderMapper.orderEntitytoOrderDto(any())).thenReturn(orderDto);

        OrderDto orderDto = orderService.saveOrder(orderToSaveDto);

        assertThat(orderDto).isNotNull();
        assertThat(orderDto.id()).isEqualTo(1L);
    }

    @Test
    void testUpdateOrder() {
        given(orderRepository.findById(order.getId())).willReturn(Optional.of(order));

        when(orderMapper.orderEntitytoOrderDto(any())).thenReturn(orderDto);

        OrderDto updatedOrder = orderService.updateOrder(order.getId(), orderToSaveDto);

        assertEquals(orderDto, updatedOrder);
    }

    @Test
    void testSearchOrderByIdFound() {
        given(orderRepository.findById(order.getId())).willReturn(Optional.of(order));
        when(orderMapper.orderEntitytoOrderDto(order)).thenReturn(orderDto);

        OrderDto orderFound = orderService.searchOrderById(order.getId());

        assertEquals(orderDto, orderFound);
    }

    @Test
    void testSearchOrderByIdNotFound() {
        when(orderRepository.findById(order.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> orderService.searchOrderById(order.getId()));
    }

    @Test
    void testGetAllOrder() {
        List<Order> orders = new ArrayList<>();
        orders.add(order);

        when(orderRepository.findAll()).thenReturn(orders);
        when(orderMapper.orderEntitytoOrderDto(order)).thenReturn(orderDto);

        List<OrderDto> allOrders = orderService.getAllOrder();

        assertEquals(orderDto, allOrders.get(0));
    }

    @Test
    void testSearchOrderByClientId(){
        List<Order> orders = new ArrayList<>();
        orders.add(order);

        given(orderRepository.findOrdersByClientId(order.getClient().getId())).willReturn(orders);

        when(orderMapper.orderEntitytoOrderDto(order)).thenReturn(orderDto);

        List<OrderDto> orderFound = orderService.searchByClientId(order.getClient().getId());

        assertEquals(List.of(orderDto), orderFound);
    }

    @Test
    void testSearchOrderByClientIdNotFound() {
        //when(orderRepository.findOrdersByClientId(order.getClient().getId())).thenReturn(new ArrayList<>());

        assertThrows(NotFoundException.class, () -> orderService.searchOrderById(order.getClient().getId()));
    }

    @Test
    void testSearchBetweenDates(){
        LocalDateTime date1 = LocalDateTime.now();
        LocalDateTime date2 = LocalDateTime.now().withYear(2022);
        List<Order> orders = new ArrayList<>();
        orders.add(order);

        when(orderRepository.FindIntoDate(date1, date2)).thenReturn(orders);

        List<OrderDto> result = orderService.searchBetweenDates(date1, date2);

        assertEquals(1, result.size());
    }
    @Test
    void testDeleteOrder() {
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        orderService.deleteOrder(order.getId());

        verify(orderRepository).delete(order);
    }

    @Test
    public void testDeleteOrderNotFound() {
        when(orderRepository.findById(order.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> orderService.deleteOrder(order.getId()));
    }
}
