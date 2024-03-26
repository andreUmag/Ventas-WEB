package app.ventasproject.repositorys;

import app.ventasproject.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public class OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
