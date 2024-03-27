package app.ventasproject.repositorys;
import app.ventasproject.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("SELECT oi FROM OrderItem oi WHERE oi.order = ?1")
    List<OrderItem> findOrderItemByOrder(Long id);
    @Query("SELECT oi FROM OrderItem oi WHERE oi.product = ?1")
    List<OrderItem> findOrderItemByProduct(Long product);
}
