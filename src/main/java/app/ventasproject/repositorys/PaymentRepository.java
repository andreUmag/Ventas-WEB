package app.ventasproject.repositorys;

import app.ventasproject.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public class PaymentRepository extends JpaRepository<Payment, Long> {
}
