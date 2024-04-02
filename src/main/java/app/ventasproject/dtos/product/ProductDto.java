package app.ventasproject.dtos.product;
import app.ventasproject.dtos.orderItem.OrderItemDto;
import java.util.Collections;
import java.util.List;

public record ProductDto(Long id, String name, Float price, Integer stock, List<OrderItemDto> orderItems) {
    public List<OrderItemDto> getOrderItems(){
        return Collections.unmodifiableList(orderItems);
    }
}
