package app.ventasproject.repositoryIntegrationTest;

import app.ventasproject.Enum.StatusEnum;
import app.ventasproject.models.Order;
import app.ventasproject.models.OrderItem;
import app.ventasproject.models.Product;
import app.ventasproject.repositories.OrderItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@Testcontainers
public class OrderItemRepositoryTest {
    OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemRepositoryTest(OrderItemRepository orderItemRepository){
        this.orderItemRepository = orderItemRepository;
    }

    @BeforeEach
    void setUp(){
        orderItemRepository.deleteAll();
    }

    @Test
    void findOrderItemByOrderID(){
        Order order1 = Order.builder()
                .status(StatusEnum.ENVIADO).build();

        OrderItem orderitem1 = OrderItem.builder()
                .order(order1)
                .build();

        orderItemRepository.save(orderitem1);

        List<OrderItem> foundItems = orderItemRepository.findOrderItemByOrder(order1.getId());

        assertEquals(1, foundItems.size());
        assertEquals(orderitem1.getId(),foundItems.get(0).getId());
    }

    @Test
    void findOrderItemBySpecificProduct(){
        Product product1 = Product.builder()
                .name("Pepsi")
                .build();

        OrderItem orderItem1 = OrderItem.builder()
                .product(product1)
                .build();

        orderItemRepository.save(orderItem1);

        List<OrderItem> foundItems = orderItemRepository.findOrderItemByProduct(orderItem1.getProduct());

        assertEquals(1, foundItems.size());
    }

    @Test
    void deleteOrderItembyID(){
        OrderItem orderItem1 = OrderItem.builder().build();

        orderItemRepository.save(orderItem1);

        orderItemRepository.deleteById(orderItem1.getId());

        assertFalse(orderItemRepository.existsById(orderItem1.getId()));
    }
}
