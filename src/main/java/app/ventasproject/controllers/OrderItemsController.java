package app.ventasproject.controllers;

import app.ventasproject.dtos.orderItem.OrderItemDto;
import app.ventasproject.dtos.orderItem.OrderItemToSaveDto;
import app.ventasproject.exceptions.NotAbleToDeleteException;
import app.ventasproject.exceptions.NotFoundException;
import app.ventasproject.services.serviceInterface.OrderItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-items")
public class OrderItemsController {
    private static final Logger logger = LoggerFactory.getLogger(OrderItemsController.class);
    private final OrderItemService orderItemService;
    @Autowired
    public OrderItemsController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PostMapping()
    public ResponseEntity<OrderItemDto> saveOrderItem(@RequestBody OrderItemToSaveDto OrderItemSaveDto){
        OrderItemDto orderItemCreated = orderItemService.saveOrderItem(OrderItemSaveDto);
        logger.info(orderItemCreated.toString());
        return ResponseEntity.ok().body(orderItemCreated);
    }

    @GetMapping()
    public ResponseEntity<List<OrderItemDto>> getOrderItem(){
        List<OrderItemDto> orderItemDtoList = orderItemService.getAllOrdenItem();
        orderItemDtoList.forEach((orderItemDto -> logger.info(orderItemDto.toString())));
        return ResponseEntity.ok().body(orderItemDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemDto> getOrderItem(@PathVariable("id") long id){
        try {
            OrderItemDto orderItemDto = orderItemService.searchOrderItemById(id);
            return ResponseEntity.ok().body(orderItemDto);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<OrderItemDto> updateOrderItem(@PathVariable("id") Long id, @RequestBody OrderItemToSaveDto OrderItemSaveDto){
        try {
            OrderItemDto orderItemUpdate = orderItemService.updateOrderItem(id, OrderItemSaveDto);
            return ResponseEntity.ok().body(orderItemUpdate);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItemDto>> getOrderItemByOrderId(@PathVariable("orderId") long id){
        List<OrderItemDto> orderItemDto = orderItemService.searchOrderItemByOrderId(id);
        return ResponseEntity.ok().body(orderItemDto);
    }

    @GetMapping("/product/{idProduct}")
    public ResponseEntity<List<OrderItemDto>> getOrderItemByProductId(@RequestParam("idProduct") Long Product){
        List<OrderItemDto> orderItemDtoList = orderItemService.searchOrderItemByProductId(Product);
        return ResponseEntity.ok().body(orderItemDtoList);
    }
    public ResponseEntity<String> deleteOrderItem(@PathVariable("id") Long id){
        try {
            orderItemService.deleteOrderItem(id);
            return ResponseEntity.ok().body("eliminado.");
        } catch (NotAbleToDeleteException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
