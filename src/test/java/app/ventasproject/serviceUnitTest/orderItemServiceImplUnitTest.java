package app.ventasproject.serviceUnitTest;

import app.ventasproject.Enum.StatusEnum;
import app.ventasproject.dtos.client.ClientDto;
import app.ventasproject.dtos.client.ClientToSaveDto;
import app.ventasproject.dtos.orderDetail.OrderDetailDto;
import app.ventasproject.dtos.orderItem.OrderItemDto;
import app.ventasproject.dtos.orderItem.OrderItemMapper;
import app.ventasproject.dtos.orderItem.OrderItemToSaveDto;
import app.ventasproject.exceptions.NotFoundException;
import app.ventasproject.models.Order;
import app.ventasproject.models.OrderDetail;
import app.ventasproject.models.OrderItem;
import app.ventasproject.models.Product;
import app.ventasproject.repositories.OrderItemRepository;
import app.ventasproject.services.serviceImplement.OrderItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class orderItemServiceImplUnitTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private OrderItemMapper orderItemMapper;

    @InjectMocks
    private OrderItemServiceImpl orderItemService;

    private Order order;
    private Product product;
    private OrderItem orderItem;
    private OrderItemDto orderItemDto;
    private OrderItemToSaveDto orderItemToSaveDto;

    @BeforeEach
    void setUp(){
        order = Order.builder().id(1L).status(StatusEnum.ENVIADO).build();
        product = Product.builder().id(1L).name("Pepsi").build();

        orderItem = OrderItem.builder()
                .id(1L)
                .unitPrice(200F)
                .order(order)
                .product(product)
                .quantity(4).build();

        orderItemToSaveDto = new OrderItemToSaveDto(1L,4,200F);

        orderItemDto = OrderItemMapper.INSTANCE.orderItemEntitytoOrderItemDto(orderItem);
    }

    @Test
    void givenOrderItem_whenSaveOrderItem_thenReturnSavedOrderItem() {
        given(orderItemRepository.save(any())).willReturn(orderItem);

        when(orderItemMapper.orderItemEntitytoOrderItemDto(any())).thenReturn(orderItemDto);

        OrderItemDto orderItemDto = orderItemService.saveOrderItem(orderItemToSaveDto);

        assertThat(orderItemDto).isNotNull();
        assertThat(orderItemDto.id()).isEqualTo(1L);
    }

    @Test
    void testUpdateOrderItem() {
        given(orderItemRepository.findById(orderItem.getId())).willReturn(Optional.of(orderItem));

        when(orderItemMapper.orderItemEntitytoOrderItemDto(any())).thenReturn(orderItemDto);

        OrderItemDto updatedOrderItem = orderItemService.updateOrderItem(orderItem.getId(), orderItemToSaveDto);

        assertEquals(orderItemDto, updatedOrderItem);
    }

    @Test
    void testSearchOrderItemByIdFound() {
        given(orderItemRepository.findById(orderItem.getId())).willReturn(Optional.of(orderItem));
        when(orderItemMapper.orderItemEntitytoOrderItemDto(orderItem)).thenReturn(orderItemDto);

        OrderItemDto orderItemFound = orderItemService.searchOrderItemById(orderItem.getId());

        assertEquals(orderItemDto, orderItemFound);
    }

    @Test
    void testSearchOrderItemByIdNotFound() {
        when(orderItemRepository.findById(orderItem.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> orderItemService.searchOrderItemById(orderItem.getId()));
    }

    @Test
    void testGetAllOrderItem() {
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);

        when(orderItemRepository.findAll()).thenReturn(orderItems);
        when(orderItemMapper.orderItemEntitytoOrderItemDto(orderItem)).thenReturn(orderItemDto);

        List<OrderItemDto> allOrdersItem = orderItemService.getAllOrdenItem();

        assertEquals(orderItemDto, allOrdersItem.get(0));
    }

    @Test
    void testSearchOrderItemByOrderIdNotFound() {
        when(orderItemRepository.findOrderItemByOrder(orderItem.getOrder().getId())).thenReturn(new ArrayList<>());

        assertThrows(NotFoundException.class, () -> orderItemService.searchOrderItemByOrderId(orderItem.getOrder().getId()));
    }

    @Test
    void testSearchOrderItemByOrderIdFound() {
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);

        given(orderItemRepository.findOrderItemByOrder(orderItem.getOrder().getId())).willReturn((orderItems));

        when(orderItemMapper.orderItemEntitytoOrderItemDto(orderItem)).thenReturn(orderItemDto);

        List<OrderItemDto> orderItemFound = orderItemService.searchOrderItemByOrderId(orderItem.getOrder().getId());

        assertEquals(List.of(orderItemDto), orderItemFound);
    }

    @Test
    void testSearchOrderItemByProductId(){
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);

        given(orderItemRepository.findOrderItemByProduct(orderItem.getProduct().getId())).willReturn(orderItems);

        when(orderItemMapper.orderItemEntitytoOrderItemDto(orderItem)).thenReturn(orderItemDto);

        List<OrderItemDto> orderItemFound = orderItemService.searchOrderItemByProductId(orderItem.getProduct().getId());

        assertEquals(List.of(orderItemDto), orderItemFound);
    }

    @Test
    void testSearchOrderItemByProductIdNotFound() {
        when(orderItemRepository.findOrderItemByProduct(orderItem.getProduct().getId())).thenReturn(new ArrayList<>());

        assertThrows(NotFoundException.class, () -> orderItemService.searchOrderItemByProductId(orderItem.getProduct().getId()));
    }

    @Test
    void testRemoveOrderItem() {
        when(orderItemRepository.findById(orderItem.getId())).thenReturn(Optional.of(orderItem));

        orderItemService.deleteOrderItem(orderItem.getId());
    }
}
