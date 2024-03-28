package app.ventasproject.dto.product;
import app.ventasproject.dto.orderItem.OrderItemDto;
import java.util.Collections;
import java.util.List;

public record ProductDto(Long id, String name, Float price, Integer stock, List<OrderItemDto> orderItems) {
    public List<OrderItemDto> orderItems(){
        return Collections.unmodifiableList(orderItems);
    }
}
