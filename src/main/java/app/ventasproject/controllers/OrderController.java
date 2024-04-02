package app.ventasproject.controllers;

import app.ventasproject.dtos.order.OrderDto;
import app.ventasproject.dtos.order.OrderToSaveDto;
import app.ventasproject.exceptions.NotAbleToDeleteException;
import app.ventasproject.exceptions.NotFoundException;
import app.ventasproject.services.serviceInterface.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping()
    public ResponseEntity<OrderDto> saveOrder(@RequestBody OrderToSaveDto OrderSaveDto){
        OrderDto orderCreated = orderService.saveOrder(OrderSaveDto);
        logger.info(orderCreated.toString());
        return ResponseEntity.ok().body(orderCreated);
    }

    @GetMapping()
    public ResponseEntity<List<OrderDto>> getOrder(){
        List<OrderDto> orderDtoList = orderService.getAllOrder();
        orderDtoList.forEach((orderDto -> logger.info(orderDto.toString())));
        return ResponseEntity.ok().body(orderDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable("id") long id){
        try {
            OrderDto orderDto = orderService.searchOrderById(id);
            return ResponseEntity.ok().body(orderDto);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable("id") Long id, @RequestBody OrderToSaveDto OrderSaveDto){
        try {
            OrderDto orderUpdate = orderService.updateOrder(id, OrderSaveDto);
            return ResponseEntity.ok().body(orderUpdate);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/customers/{customerId}")
    public  ResponseEntity<List<OrderDto>> searchOrderByClientId(@PathVariable("customerId") Long id){
        List<OrderDto> OrderDtoList =orderService.searchByClientId(id);
        return ResponseEntity.ok().body(OrderDtoList);
    }

    @GetMapping("/date-range")
    public  ResponseEntity<List<OrderDto>> searchOrderIntoDate(@RequestParam("startDate") LocalDateTime startDate, @RequestParam("endDate") LocalDateTime endDate){
        List<OrderDto> OrderDtoList = orderService.searchBetweenDates(startDate, endDate);
        return ResponseEntity.ok().body(OrderDtoList);
    }
    public ResponseEntity<String> deleteOrder(@PathVariable("id") Long id){
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok().body("eliminado.");
        } catch (NotAbleToDeleteException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
