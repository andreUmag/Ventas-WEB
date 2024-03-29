package app.ventasproject.dto.client;
import app.ventasproject.dto.order.OrderDto;
import app.ventasproject.models.Order;
import java.util.List;
import java.util.Collections;

public record ClientDto(Long id, String name, String email,  String address, List<OrderDto> orders) {
    public List<OrderDto> getOrders(){
        return Collections.unmodifiableList(orders);
    }
}
