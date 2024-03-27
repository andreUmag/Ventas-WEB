package app.ventasproject.repositoryIntegrationTest;

import app.ventasproject.Enum.StatusEnum;
import app.ventasproject.models.Client;
import app.ventasproject.models.Order;
import app.ventasproject.repositorys.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Testcontainers
public class OrderRepositoryTest {
    OrderRepository orderRepository;

    @Autowired
    public OrderRepositoryTest(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }
    @BeforeEach
    void setUp(){
        orderRepository.deleteAll();
    }

    @Test
    void findOrderBetweenDate(){
        LocalDateTime Date1 = LocalDateTime.of(2022,1,1,0,0);
        LocalDateTime Date2 = LocalDateTime.of(2024,1,1,0,0);

        LocalDateTime orderDate = LocalDateTime.of(2023,12,21,0,0);
        Order order1 = Order.builder()
                .orderDate(orderDate)
                .status(StatusEnum.ENVIADO).build();

        orderRepository.save(order1);

        List<Order> foundOrders = orderRepository.FindIntoDate(Date1,Date2);

        assertEquals(orderDate, foundOrders.get(0).getOrderDate());
    }

    @Test
    void findOrderByClientandStatus(){
        Client client1 = Client.builder()
                .name("len")
                .email("ls@umg.com")
                .address("P.sherman calle wallaby 42 Sydney").build();

        Order order1 = Order.builder()
                .client(client1)
                .status(StatusEnum.ENVIADO).build();

        orderRepository.save(order1);

        List<Order> foundOrders = orderRepository.FindClientAndStatus(client1.getId(), StatusEnum.ENVIADO.name());

        assertEquals(1, foundOrders.size());
    }
}
