package app.ventasproject.controllers;

import app.ventasproject.dtos.payment.PaymentDto;
import app.ventasproject.dtos.payment.PaymentToSaveDto;
import app.ventasproject.exceptions.NotAbleToDeleteException;
import app.ventasproject.exceptions.NotFoundException;
import app.ventasproject.services.serviceInterface.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping()
    public ResponseEntity<PaymentDto> savePayment(@RequestBody PaymentToSaveDto paymentSaveDto){
        PaymentDto paymentCreated = paymentService.savePayments(paymentSaveDto);
        logger.info(paymentCreated.toString());
        return ResponseEntity.ok().body(paymentCreated);
    }

    @GetMapping()
    public ResponseEntity<List<PaymentDto>> getPayment(){
        List<PaymentDto> paymentDtoList = paymentService.getAllPayment();
        paymentDtoList.forEach((paymentDto -> logger.info(paymentDto.toString())));
        return ResponseEntity.ok().body(paymentDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getPayment(@PathVariable("id") long id){
        try {
            PaymentDto paymentDto = paymentService.searchPaymentById(id);
            return ResponseEntity.ok().body(paymentDto);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<PaymentDto> updatePayment(@PathVariable("id") Long id, @RequestBody PaymentToSaveDto paymentSaveDto){
        try {
            PaymentDto paymentUpdate = paymentService.updatePatyment(id, paymentSaveDto);
            return ResponseEntity.ok().body(paymentUpdate);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentDto> SearchPaymentByIdOrder(@PathVariable("orderId") Long id){
        try {
            PaymentDto paymentDto = paymentService.searchPaymentByOrderId(id);
            return ResponseEntity.ok().body(paymentDto);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<PaymentDto>> searchPaymentIntoDate(@RequestParam("startDate") LocalDateTime Date){
        List<PaymentDto> paymentDtoList = paymentService.sarchByIntoDates(Date);
        return ResponseEntity.ok().body(paymentDtoList);
    }

    public ResponseEntity<String> deletePayment(@PathVariable("id") Long id){
        try {
            paymentService.deletePayment(id);
            return ResponseEntity.ok().body("eliminado.");
        } catch (NotAbleToDeleteException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
