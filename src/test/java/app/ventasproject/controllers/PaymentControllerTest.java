package app.ventasproject.controllers;

import app.ventasproject.Enum.MethodsEnum;
import app.ventasproject.dtos.payment.PaymentDto;
import app.ventasproject.dtos.payment.PaymentMapper;
import app.ventasproject.dtos.payment.PaymentToSaveDto;
import app.ventasproject.models.Order;
import app.ventasproject.models.Payment;
import app.ventasproject.services.serviceInterface.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.github.dockerjava.core.MediaType;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PaymentController.class)
@ExtendWith(MockitoExtension.class)
class PaymentControllerTest extends ModelsTest{
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Autowired
    ObjectMapper objectMapper;

    private Order order;
    private Payment payment, payment2;
    private PaymentDto paymentDto;

    @BeforeEach
    void setUp() {
        order = Order.builder().id(1L).build();
        payment = Payment.builder()

                .id(1L)
                .totalPayment(2600F)
                .datePayment(LocalDateTime.now())
                .method(MethodsEnum.EFECTIVO)
                .order(order)
                .build();

        payment2 = Payment.builder()
                .id(2L)
                .totalPayment(2500F)
                .datePayment(LocalDateTime.now())
                .method(MethodsEnum.PAYPAL)
                .order(order)
                .build();

        paymentDto = PaymentMapper.INSTANCE.paymentEntitytoOPaymentDto(payment);
    }

    @Test
    void savePayment() throws Exception { //no sale
        PaymentToSaveDto paymentToSaveDto = new PaymentToSaveDto(
                null,
                5000F,
                LocalDateTime.now(),
                MethodsEnum.EFECTIVO
        );
        when(paymentService.savePayments(paymentToSaveDto)).thenReturn(paymentDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/payments")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(paymentToSaveDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getPayment() throws Exception {
        List<PaymentDto> payment = Collections.singletonList(paymentDto);
        when(paymentService.getAllPayment()).thenReturn(payment);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/payments")
                .contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void testGetPaymentById() throws Exception{
        when(paymentService.searchPaymentById(anyLong())).thenReturn(paymentDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/payments/1")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void updatePayment() throws Exception{ //no sale
        PaymentToSaveDto paymentToSaveDto = new PaymentToSaveDto(
                1L,
                5000.0F,
                LocalDateTime.now(),
                MethodsEnum.EFECTIVO
        );

        when(paymentService.updatePayment(anyLong(), any())).thenReturn(paymentDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/payments/1")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(paymentToSaveDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void searchPaymentByIdOrder() throws Exception{
        when(paymentService.searchPaymentByOrderId(anyLong())).thenReturn(paymentDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/payments/order/1")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void searchPaymentIntoDate() throws Exception{
        List<PaymentDto> payment = Collections.singletonList(paymentDto);

        when(paymentService.searchByIntoDates(any(), any())).thenReturn(payment);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/payments/date-range")
                        .param("startDate", LocalDateTime.now().minusDays(7).toString())
                        .param("endDate", LocalDateTime.now().toString())
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void deletePayment() throws Exception{ //no sale
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/payments/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}