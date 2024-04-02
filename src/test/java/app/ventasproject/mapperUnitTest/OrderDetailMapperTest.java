package app.ventasproject.mapperUnitTest;

import app.ventasproject.dtos.orderDetail.OrderDetailDto;
import app.ventasproject.dtos.orderDetail.OrderDetailMapper;
import app.ventasproject.models.OrderDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrderDetailMapperTest {
    OrderDetail orderDetail;
    OrderDetailDto orderDetailDto;

    @BeforeEach
    void setUp(){
        orderDetail = OrderDetail.builder()
                .addressOrder("calle 19")
                .transporter("rapid")
                .guide("ab2").build();

        orderDetailDto = new OrderDetailDto(
                1L,
                "calle 19",
                "rapid",
                "ab2"
                );
    }
    @Test
    public void orderDetailEntitytoDTO(){
        OrderDetailDto orderDetailDto = OrderDetailMapper.INSTANCE.orderDetailEntitytoOrderDetailDto(orderDetail);

        assertNotNull(orderDetailDto);
        assertEquals(orderDetail.getAddressOrder(),orderDetailDto.addressOrder());
    }

    @Test
    public void orderDetailDTOtoEntity(){
        OrderDetail orderDetail = OrderDetailMapper.INSTANCE.orderDetailDtotoOrderDetailEntity(orderDetailDto);

        assertNotNull(orderDetail);
        assertTrue(orderDetail.getTransporter().equalsIgnoreCase("Rapid"));
    }
}
