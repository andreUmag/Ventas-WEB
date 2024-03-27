package app.ventasproject.repositorys;

import app.ventasproject.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT p FROM Payment p WHERE p.datePayment BETWEEN CURRENT_TIMESTAMP AND ?1")
    List<Payment> FindByIntoDates(LocalDateTime date);
    @Query("SELECT p FROM Payment p WHERE p.order = ?1 AND p.method in ?2")
    Payment FindByOrderAndMethod(Long id, String method);
}
