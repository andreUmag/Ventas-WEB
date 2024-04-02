package app.ventasproject.services.serviceImplement;

import app.ventasproject.dtos.payment.PaymentDto;
import app.ventasproject.dtos.payment.PaymentMapper;
import app.ventasproject.dtos.payment.PaymentToSaveDto;
import app.ventasproject.exceptions.NotFoundException;
import app.ventasproject.models.Order;
import app.ventasproject.models.Payment;
import app.ventasproject.repositories.PaymentRepository;
import app.ventasproject.services.serviceInterface.PaymentService;

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
    public PaymentDto updatePatyment(Long id, PaymentToSaveDto paymentDto) {
        return paymentRepository.findById(id).
                map( payment -> {
                            payment.setDatePayment(paymentDto.datePayment());
                            payment.setMethod(paymentDto.method());
                            payment.setTotalPayment(paymentDto.totalPayment());
                            Payment paymentSave = paymentRepository.save(payment);
                            return  paymentMapper.paymentEntitytoOPaymentDto(paymentSave);
                        }
                ).orElseThrow(() -> new NotFoundException("Pago no encontrado."));
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
    public PaymentDto searchPaymentByOrderId(Long id){
        Payment payment = paymentRepository.FindByOrderId(id);
        return paymentMapper.paymentEntitytoOPaymentDto(payment);
    }

    @Override
    public List<PaymentDto> sarchByIntoDates(LocalDateTime date) {
        return paymentRepository.FindByIntoDates(date)
                .stream()
                .map(payment -> paymentMapper.paymentEntitytoOPaymentDto(payment))
                .toList();
    }

    @Override
    public void deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow();
        paymentRepository.delete(payment);
    }
}
