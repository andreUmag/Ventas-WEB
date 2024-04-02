package app.ventasproject.mapperUnitTest;

import app.ventasproject.Enum.MethodsEnum;
import app.ventasproject.dtos.payment.PaymentDto;
import app.ventasproject.dtos.payment.PaymentMapper;
import app.ventasproject.models.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PaymentMapperTest {
    Payment payment;
    PaymentDto paymentDto;
    @BeforeEach
    void setUp(){
        payment = Payment.builder()
                .totalPayment(230F)
                .datePayment(LocalDateTime.now())
                .method(MethodsEnum.NEQUI)
                .build();

        paymentDto = new PaymentDto(
                1L,
                230F,
                LocalDateTime.now(),
                MethodsEnum.NEQUI);

    }

    @Test
    public void paymentEntitytoDTO(){
        PaymentDto paymentDto = PaymentMapper.INSTANCE.paymentEntitytoOPaymentDto(payment);

        assertNotNull(paymentDto);
        assertEquals(payment.getTotalPayment(), paymentDto.totalPayment());
    }

    @Test
    public void paymentDTOtoEntity(){
        Payment payment = PaymentMapper.INSTANCE.paymentDtotoPaymentEntity(paymentDto);

        assertNotNull(payment);
        assertEquals(paymentDto.method(),payment.getMethod());
    }

}
