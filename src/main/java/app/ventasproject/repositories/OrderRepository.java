package app.ventasproject.repositories;
import app.ventasproject.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.orderDate BETWEEN ?1 and ?2")
    List<Order> FindIntoDate(LocalDateTime fecha, LocalDateTime fecha2);
    @Query("SELECT o FROM Order o WHERE o.client = ?1 AND o.status in ?2")
    List<Order> FindClientAndStatus(Long client, String status);
    @Query("SELECT o FROM Order o JOIN FETCH o.orderItems WHERE o.client.id = ?1")
    List<Order> findOrdersWithItemsByClient(Long clientId); //no estoy seguro de esta
    @Query("SELECT o FROM Order o WHERE o.client.id = ?1")
    List<Order> findOrdersByClientId(Long id);
}
