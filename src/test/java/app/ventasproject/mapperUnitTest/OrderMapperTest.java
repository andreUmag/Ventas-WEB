package app.ventasproject.mapperUnitTest;

import app.ventasproject.Enum.StatusEnum;
import app.ventasproject.dtos.order.OrderDto;
import app.ventasproject.dtos.order.OrderMapper;
import app.ventasproject.models.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class OrderMapperTest {
    Order order;
    OrderDto orderDto;

    @BeforeEach
    void setUp(){
        LocalDateTime date = LocalDateTime.now();
        order = Order.builder().build();

        orderDto = new OrderDto(1L,date, StatusEnum.ENVIADO,null,null,null);
    }
    @Test
    public void orderEntitytoDto(){
        OrderDto orderDto = OrderMapper.INSTANCE.orderEntitytoOrderDto(order);

        assertNotNull(orderDto);
        assertEquals(order.getStatus(), orderDto.status());
    }
    @Test
    public void orderDTOtoEntity(){
        Order order = OrderMapper.INSTANCE.orderDtotoOrderEntity(orderDto);

        assertNotNull(order);
        assertEquals(orderDto.id(),order.getId());
    }
}
