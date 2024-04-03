package app.ventasproject.repositories;

import app.ventasproject.models.Order;
import app.ventasproject.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT p FROM Payment p WHERE p.datePayment BETWEEN ?1 AND ?2")
    List<Payment> FindByIntoDates(LocalDateTime date, LocalDateTime date2);
    @Query("SELECT p FROM Payment p WHERE p.order = ?1 AND p.method in ?2")
    Payment FindByOrderAndMethod(Long id, String method);
    @Query("SELECT P FROM Payment P WHERE P.order = ?1")
    Payment FindByOrderId(Long id);
}
