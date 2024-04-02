package app.ventasproject.service.serviceImplement;

import app.ventasproject.dto.payment.PaymentDto;
import app.ventasproject.dto.payment.PaymentMapper;
import app.ventasproject.dto.payment.PaymentToSaveDto;
import app.ventasproject.exception.NotFoundException;
import app.ventasproject.models.Order;
import app.ventasproject.models.Payment;
import app.ventasproject.repositorys.PaymentRepository;
import app.ventasproject.service.serviceInterface.PaymentService;

import java.time.LocalDateTime;
import java.util.List;

public class PaymentsServiceImpl implements PaymentService {
    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;

    public PaymentsServiceImpl(PaymentMapper paymentMapper, PaymentRepository paymentRepository) {
        this.paymentMapper = paymentMapper;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentDto savePayments(PaymentToSaveDto paymentDto) {
        Payment payment = paymentMapper.paymentToSaveDToToEntity(paymentDto);
        Payment paymentSaved = paymentRepository.save(payment);
        return paymentMapper.paymentEntitytoOPaymentDto(paymentSaved);
    }

    @Override
    public PaymentDto updatePatyment(Long id, PaymentToSaveDto payment) {
        return null;
    }

    @Override
    public PaymentDto searchPaymentById(Long id) throws NotFoundException {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new NotFoundException("No encontrado"));
        return paymentMapper.paymentEntitytoOPaymentDto(payment);
    }

    @Override
    public List<PaymentDto> getAllPayment() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream()
                .map(payment -> paymentMapper.paymentEntitytoOPaymentDto(payment))
                .toList();
    }

    @Override
    public PaymentDto searchPaymentByOrderId(Order id) {
        return null;
    }

    @Override
    public List<PaymentDto> sarchByIntoDates(LocalDateTime date) {
        return null;
    }

    @Override
    public void deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow();
        paymentRepository.delete(payment);
    }
}
