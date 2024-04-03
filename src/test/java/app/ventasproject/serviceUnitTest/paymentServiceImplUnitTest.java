package app.ventasproject.serviceUnitTest;

import app.ventasproject.dtos.order.OrderDto;
import app.ventasproject.dtos.payment.PaymentDto;
import app.ventasproject.dtos.payment.PaymentMapper;
import app.ventasproject.dtos.payment.PaymentToSaveDto;
import app.ventasproject.exceptions.NotFoundException;
import app.ventasproject.models.Order;
import app.ventasproject.models.Payment;
import app.ventasproject.repositories.PaymentRepository;
import app.ventasproject.services.serviceImplement.PaymentsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class paymentServiceImplUnitTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentMapper paymentMapper;

    @InjectMocks
    private PaymentsServiceImpl paymentsService;

    private Payment payment, payment2;
    private Order order;
    private PaymentDto paymentDto, paymentDto2;
    private PaymentToSaveDto paymentToSaveDto;

    @BeforeEach
    void setUp(){
        order = Order.builder().id(1L).build();
        payment = Payment.builder().id(1L).order(order).datePayment(LocalDateTime.now().minusDays(11)).build();
        payment2 = Payment.builder().id(2L).order(order).datePayment(LocalDateTime.now().minusDays(5)).build();

        paymentToSaveDto = new PaymentToSaveDto(
                payment.getId(),
                payment.getTotalPayment(),
                payment.getDatePayment(),
                payment.getMethod());

        paymentDto = PaymentMapper.INSTANCE.paymentEntitytoOPaymentDto(payment);
        paymentDto2 = PaymentMapper.INSTANCE.paymentEntitytoOPaymentDto(payment2);
    }

    @Test
    void testSavedPayment() {
        given(paymentRepository.save(any())).willReturn(payment);

        when(paymentMapper.paymentEntitytoOPaymentDto(any())).thenReturn(paymentDto);

        PaymentDto paymentDto = paymentsService.savePayments(paymentToSaveDto);

        assertThat(paymentDto).isNotNull();
        assertThat(paymentDto.id()).isEqualTo(1L);
    }

    @Test
    void testUpdateOrder() {
        given(paymentRepository.findById(payment.getId())).willReturn(Optional.of(payment));

        when(paymentMapper.paymentEntitytoOPaymentDto(any())).thenReturn(paymentDto);

        PaymentDto updatedPayment = paymentsService.updatePayment(payment.getId(), paymentToSaveDto);

        assertEquals(paymentDto, updatedPayment);
    }

    @Test
    void testSearchPaymentByIdFound() {
        given(paymentRepository.findById(payment.getId())).willReturn(Optional.of(payment));
        when(paymentMapper.paymentEntitytoOPaymentDto(payment)).thenReturn(paymentDto);

        PaymentDto paymentFound = paymentsService.searchPaymentById(payment.getId());

        assertEquals(paymentDto, paymentFound);
    }

    @Test
    void testSearchPaymentByIdNotFound() {
        when(paymentRepository.findById(payment.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> paymentsService.searchPaymentById(payment.getId()));
    }

    @Test
    void testGetAllPayments() {
        List<Payment> payments = new ArrayList<>();
        payments.add(payment);

        when(paymentRepository.findAll()).thenReturn(payments);
        when(paymentMapper.paymentEntitytoOPaymentDto(payment)).thenReturn(paymentDto);

        List<PaymentDto> allPayments = paymentsService.getAllPayment();

        assertEquals(paymentDto, allPayments.get(0));
    }

    @Test
    void testSearchPaymentByOrderId(){

        given(paymentRepository.FindByOrderId(payment.getOrder().getId())).willReturn(payment);

        when(paymentMapper.paymentEntitytoOPaymentDto(payment)).thenReturn(paymentDto);

        PaymentDto paymentFound = paymentsService.searchPaymentByOrderId(payment.getOrder().getId());

        assertEquals(paymentDto, paymentFound);
    }

    @Test
    void testSearchPaymentByOrderIdNotFound() {
        //given(paymentRepository.FindByOrderId(payment.getOrder().getId())).willReturn(payment);

        assertThrows(NotFoundException.class, () -> paymentsService.searchPaymentByOrderId(payment.getOrder().getId()));
    }

    @Test
    void testSearchByIntoDates(){
        LocalDateTime date1 = LocalDateTime.now().minusDays(110);
        LocalDateTime date2 = LocalDateTime.now();
        List<Payment> payments = new ArrayList<>();
        payments.add(payment);
        payments.add(payment2);

        given(paymentRepository.FindByIntoDates(date1, date2)).willReturn(payments);
        given(paymentMapper.paymentEntitytoOPaymentDto(payment)).willReturn(paymentDto);
        given(paymentMapper.paymentEntitytoOPaymentDto(payment2)).willReturn(paymentDto2);

        List<PaymentDto> result = paymentsService.searchByIntoDates(date1, date2);

        assertEquals(2, result.size());
        System.out.println(date1 + "\n" +date2 + "\n" + result);
    }

    @Test
    void testDeletePayment() {
        when(paymentRepository.findById(payment.getId())).thenReturn(Optional.of(payment));

        paymentsService.deletePayment(payment.getId());

        verify(paymentRepository).delete(payment);
    }

    @Test
    public void testDeletePaymentNotFound() {
        when(paymentRepository.findById(payment.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> paymentsService.deletePayment(payment.getId()));
    }
}
