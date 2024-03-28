package app.ventasproject.dto.order;
import app.ventasproject.models.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
    OrderDto orderEntitytoOrderDto(Order order);
    Order orderDtotoOrderEntity(OrderDto orderDto);
}
