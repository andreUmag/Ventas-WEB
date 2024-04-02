package app.ventasproject.service.serviceInterface;


import app.ventasproject.dto.payment.PaymentDto;
import app.ventasproject.dto.payment.PaymentToSaveDto;
import app.ventasproject.exception.NotFoundException;
import app.ventasproject.models.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentService {
    PaymentDto savePayments(PaymentToSaveDto payment);
    PaymentDto updatePatyment(Long id, PaymentToSaveDto payment);
    PaymentDto searchPaymentById(Long id) throws NotFoundException;
    List<PaymentDto> getAllPayment();
    PaymentDto searchPaymentByOrderId(Order id);// no estoy seguro
    List<PaymentDto> sarchByIntoDates(LocalDateTime date);
    void deletePayment(Long id);
}
