package app.ventasproject.repositorys;

import app.ventasproject.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public class OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
