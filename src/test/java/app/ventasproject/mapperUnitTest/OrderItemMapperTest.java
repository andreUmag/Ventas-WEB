package app.ventasproject.mapperUnitTest;

import app.ventasproject.dtos.orderItem.OrderItemDto;
import app.ventasproject.dtos.orderItem.OrderItemMapper;
import app.ventasproject.models.OrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrderItemMapperTest {
    OrderItem orderItem;
    OrderItemDto orderItemDto;

    @BeforeEach
    void setUp(){
        orderItem = OrderItem.builder().quantity(233).unitPrice(300F).build();

        orderItemDto = new OrderItemDto(1L,233,300F);

    }
    @Test
    public void orderItemEntitytoDTO(){
        OrderItemDto orderItemDto = OrderItemMapper.INSTANCE.orderItemEntitytoOrderItemDto(orderItem);

        assertNotNull(orderItemDto);
        assertEquals(orderItem.getQuantity(),orderItemDto.quantity());
        assertEquals(orderItem.getUnitPrice(),orderItemDto.unitPrice());
    }
    @Test
    public void orderItemDTOtoEntity(){
        OrderItem orderItem = OrderItemMapper.INSTANCE.orderItemDtotoOrderItemEntity(orderItemDto);

        assertNotNull(orderItem);
        assertEquals(orderItemDto.quantity(),orderItem.getQuantity());
        assertEquals(orderItemDto.unitPrice(),orderItem.getUnitPrice());
    }
}
