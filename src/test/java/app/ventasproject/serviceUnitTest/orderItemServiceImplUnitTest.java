package app.ventasproject.serviceUnitTest;

import app.ventasproject.dtos.client.ClientDto;
import app.ventasproject.dtos.client.ClientToSaveDto;
import app.ventasproject.dtos.orderDetail.OrderDetailDto;
import app.ventasproject.dtos.orderItem.OrderItemDto;
import app.ventasproject.dtos.orderItem.OrderItemMapper;
import app.ventasproject.dtos.orderItem.OrderItemToSaveDto;
import app.ventasproject.exceptions.NotFoundException;
import app.ventasproject.models.OrderItem;
import app.ventasproject.repositories.OrderItemRepository;
import app.ventasproject.services.serviceImplement.OrderItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    private OrderItem orderItem;
    private OrderItemDto orderItemDto;
    private OrderItemToSaveDto orderItemToSaveDto;

    @BeforeEach
    void setUp(){
        orderItem = OrderItem.builder()
                .id(1L)
                .unitPrice(200F)
                .quantity(4).build();

        orderItemToSaveDto = new OrderItemToSaveDto(1L,4,200F);

        orderItemDto = OrderItemMapper.INSTANCE.orderItemEntitytoOrderItemDto(orderItem);
    }

    @Test
    void givenOrderItem_whenSaveOrderItem_thenReturnSavedOrderItem() {
        given(orderItemRepository.save(any())).willReturn(orderItem);

        orderItemToSaveDto = new OrderItemToSaveDto(
                1L,
                4,
                200F);

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
}
