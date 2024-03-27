package app.ventasproject.repositorys;

import app.ventasproject.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    OrderDetail findOrderDetailByOrder(Long order);
    OrderDetail findOrderDetailByTransporter(String transporter);
    //la ultima toca hacer inner join con la tabla estado para consultar el estado
}
