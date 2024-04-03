package app.ventasproject.services.serviceInterface;


import app.ventasproject.dtos.payment.PaymentDto;
import app.ventasproject.dtos.payment.PaymentToSaveDto;
import app.ventasproject.exceptions.NotFoundException;
import app.ventasproject.models.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
@Component
public interface PaymentService {
    PaymentDto savePayments(PaymentToSaveDto payment);
    PaymentDto updatePayment(Long id, PaymentToSaveDto payment);
    PaymentDto searchPaymentById(Long id) throws NotFoundException;
    List<PaymentDto> getAllPayment();
    PaymentDto searchPaymentByOrderId(Long id) throws NotFoundException;
    List<PaymentDto> searchByIntoDates(LocalDateTime date, LocalDateTime date2);
    void deletePayment(Long id);
}
