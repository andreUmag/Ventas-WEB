package app.ventasproject.repositorys;

import app.ventasproject.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public class OrderRepository extends JpaRepository<Order, Long> {
}
