package app.ventasproject.controllers;

import app.ventasproject.dtos.orderDetail.OrderDetailDto;
import app.ventasproject.dtos.orderDetail.OrderDetailToSaveDto;
import app.ventasproject.exceptions.NotAbleToDeleteException;
import app.ventasproject.exceptions.NotFoundException;
import app.ventasproject.services.serviceInterface.OrderDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shipping")
public class OrderDetailController {
    private static final Logger logger = LoggerFactory.getLogger(OrderDetailController.class);
    private final OrderDetailService orderDetailService;
    @Autowired
    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @PostMapping()
    public ResponseEntity<OrderDetailDto> saveCliente(@RequestBody OrderDetailToSaveDto orderDetailSaveDto){
        OrderDetailDto orderDetailCreated = orderDetailService.saveOrderDetail(orderDetailSaveDto);
        logger.info(orderDetailCreated.toString());
        return ResponseEntity.ok().body(orderDetailCreated);
    }

    @GetMapping()
    public ResponseEntity<List<OrderDetailDto>> getOrderDetail(){
        List<OrderDetailDto> orderDetailDtoList = orderDetailService.getAllOrderDetail();
        orderDetailDtoList.forEach((orderDetailDto -> logger.info(orderDetailDto.toString())));
        return ResponseEntity.ok().body(orderDetailDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailDto> getOrderDetailById(@PathVariable("id") long id){
        try {
            OrderDetailDto orderDetailDto = orderDetailService.searchOrderDetailById(id);
            return ResponseEntity.ok().body(orderDetailDto);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<OrderDetailDto> updateOrderDetail(@PathVariable("id") Long id, @RequestBody OrderDetailToSaveDto orderDetailSaveDto){
        try {
            OrderDetailDto orderDetailUpdate = orderDetailService.updateOrderDetail(id, orderDetailSaveDto);
            return ResponseEntity.ok().body(orderDetailUpdate);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/carrier")
    public ResponseEntity<List<OrderDetailDto>> getOrderDetailDtoByTransporter(@RequestParam("name") String Transporter){
        List<OrderDetailDto> orderDetailDtoList = orderDetailService.serachOrderDetailByTransporter(Transporter);
        orderDetailDtoList.forEach((orderDetailDto -> logger.info(orderDetailDto.toString())));
        return ResponseEntity.ok().body(orderDetailDtoList);
    }
    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderDetailDto> getOrderDetailByOrderId(@PathVariable("orderId") Long id){
        try {
            OrderDetailDto orderDetailDto = orderDetailService.searchOrderDetailByOrderId(id);
            logger.info(orderDetailDto.toString());
            return ResponseEntity.ok().body(orderDetailDto);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderDetail(@PathVariable("id") Long id){
        try {
            orderDetailService.deleteOrderDetail(id);
            return ResponseEntity.ok().body("Eliminado.");
        } catch (NotAbleToDeleteException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
