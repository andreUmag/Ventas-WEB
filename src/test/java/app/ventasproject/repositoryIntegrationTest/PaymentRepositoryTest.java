package app.ventasproject.repositoryIntegrationTest;

import app.ventasproject.Enum.MethodsEnum;
import app.ventasproject.Enum.StatusEnum;
import app.ventasproject.models.Order;
import app.ventasproject.models.OrderItem;
import app.ventasproject.models.Payment;
import app.ventasproject.repositorys.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
public class PaymentRepositoryTest {
    PaymentRepository paymentRepository;

    @Autowired
    public PaymentRepositoryTest(PaymentRepository paymentRepository){
        this.paymentRepository = paymentRepository;
    }

    @BeforeEach
    void setUp(){
        paymentRepository.deleteAll();
    }

    @Test
    void findPaymentsIntoDates(){
        LocalDateTime Date1 = LocalDateTime.of(2022,1,1,0,0);

        LocalDateTime datePayment = LocalDateTime.of(2023,8,4,0,0);
        Payment payment1 = Payment.builder()
                .datePayment(datePayment)
                .build();

        paymentRepository.save(payment1);

        List<Payment> foundPayments = paymentRepository.FindByIntoDates(Date1);

        assertEquals(1,foundPayments.size());
    }

    @Test
    void findPaymentsByOrderAndMethod(){
        Order order1 = Order.builder()
                .status(StatusEnum.ENVIADO).build();

        Payment payment1 = Payment.builder()
                .totalPayment(200f)
                .method(MethodsEnum.PSE)
                .order(order1)
                .build();

        paymentRepository.save(payment1);

        Payment foundPayment = paymentRepository.FindByOrderAndMethod(order1.getId(), MethodsEnum.PSE.name());

        assertNotNull(foundPayment);
        assertEquals(order1.getId(), foundPayment.getId());
    }

    @Test
    void deletePaymentbyID(){
        Payment payment = Payment.builder().build();

        paymentRepository.save(payment);

        paymentRepository.deleteById(payment.getId());

        assertFalse(paymentRepository.existsById(payment.getId()));
    }
}
