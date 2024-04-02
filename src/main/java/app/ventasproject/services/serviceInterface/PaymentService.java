package app.ventasproject.services.serviceInterface;


import app.ventasproject.dtos.payment.PaymentDto;
import app.ventasproject.dtos.payment.PaymentToSaveDto;
import app.ventasproject.exceptions.NotFoundException;
import app.ventasproject.models.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentService {
    PaymentDto savePayments(PaymentToSaveDto payment);
    PaymentDto updatePatyment(Long id, PaymentToSaveDto payment);
    PaymentDto searchPaymentById(Long id) throws NotFoundException;
    List<PaymentDto> getAllPayment();
    PaymentDto searchPaymentByOrderId(Long id) throws NotFoundException;
    List<PaymentDto> sarchByIntoDates(LocalDateTime date);
    void deletePayment(Long id);
}
