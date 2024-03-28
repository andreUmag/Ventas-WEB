package app.ventasproject.repositoryIntegrationTest;

import app.ventasproject.Enum.StatusEnum;
import app.ventasproject.models.Order;
import app.ventasproject.models.OrderDetail;
import app.ventasproject.repositorys.OrderDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

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


    }

    @Test
    void findOrderDetailByaTransporter(){

    }
}
