package app.ventasproject.dtos.client;
import app.ventasproject.dtos.order.OrderDto;

import java.util.List;
import java.util.Collections;

public record ClientDto(Long id, String name, String email, String password,String address, List<OrderDto> orders, String role) {
    public List<OrderDto> getOrders(){
        return Collections.unmodifiableList(orders);
    }
}
