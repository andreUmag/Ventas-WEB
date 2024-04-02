package app.ventasproject.repositoryIntegrationTest;

import app.ventasproject.Enum.StatusEnum;
import app.ventasproject.models.Order;
import app.ventasproject.models.OrderDetail;
import app.ventasproject.repositories.OrderDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
public class OrderDetailRepositoryTest {
    OrderDetailRepository orderDetailRepository;

    @Autowired
    public OrderDetailRepositoryTest(OrderDetailRepository orderDetailRepository){
        this.orderDetailRepository = orderDetailRepository;
    }

    @BeforeEach
    void setUp(){
        orderDetailRepository.deleteAll();
    }

    @Test
    void findOrderDetailByOrderID(){
        Order order1 = Order.builder()
                .status(StatusEnum.ENVIADO).build();

        OrderDetail orderDetail1 = OrderDetail.builder()
                .order(order1)
                .build();

        orderDetailRepository.save(orderDetail1);

        /*Optional<OrderDetail> foundOrderDetail = orderDetailRepository.findById(order1.getId());

        assertNotNull(foundOrderDetail);

        assertEquals(order1.getId(), foundOrderDetail.get().getId());*/
    }

    @Test
    void findOrderDetailByaTransporter(){
        OrderDetail orderDetail1 = OrderDetail.builder()
                .transporter("rapid")
                .build();

        orderDetailRepository.save(orderDetail1);
    }

    @Test
    void deleteOrderDetailbyID(){
        OrderDetail orderDetail = OrderDetail.builder()
                .id(1L)
                .addressOrder("Calle 12")
                .build();

        orderDetailRepository.save(orderDetail);

        orderDetailRepository.deleteById(1L);

        assertFalse(orderDetailRepository.existsById(1L));
    }

    @Test
    void updateOrderDetailAddressbyID(){
        OrderDetail orderDetail = OrderDetail.builder()
                .id(1L)
                .addressOrder("Calle 12")
                .build();

        orderDetailRepository.save(orderDetail);

        OrderDetail orderUpdated = orderDetailRepository.findById(1L).get();
        orderUpdated.setAddressOrder("Calle 15");

        assertEquals("Calle 15", orderUpdated.getAddressOrder());
        assertEquals(orderDetail.getId(), orderUpdated.getId());
        assertNotEquals(orderDetail.getAddressOrder(), orderUpdated.getAddressOrder());
    }
}
