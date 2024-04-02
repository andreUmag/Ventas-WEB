package app.ventasproject.repositories;

import app.ventasproject.models.Order;
import app.ventasproject.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Query("SELECT od FROM OrderDetail od WHERE od.order = ?1")
    OrderDetail findOrderDetailByOrderId(Long orderId);
    @Query("SELECT od FROM OrderDetail od WHERE od.transporter like %?1%")
    List<OrderDetail> findOrderDetailByTransporter(String transporter);
    //la ultima toca hacer inner join con la tabla estado para consultar el estado
}
