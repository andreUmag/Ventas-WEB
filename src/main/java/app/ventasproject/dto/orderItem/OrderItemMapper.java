package app.ventasproject.dto.orderItem;
import app.ventasproject.models.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderItemMapper {
    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);
    OrderItemDto orderItemEntitytoOrderItemDto(OrderItem orderItem);
    OrderItem orderItemDtotoOrderItemEntity(OrderItemDto orderItemDto);
    OrderItem orderItemToSaveDToToEntity(OrderItemToSaveDto orderItem);
}
