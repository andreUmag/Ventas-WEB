package app.ventasproject.dto.orderDetail;
import app.ventasproject.models.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderDetailMapper {
    OrderDetailMapper INSTANCE = Mappers.getMapper(OrderDetailMapper.class);
    OrderDetailDto orderDetailEntitytoOrderDetailDto(OrderDetail orderDetail);
    OrderDetail orderDetailDtotoOrderDetailEntity(OrderDetailDto orderDetailDto);

    OrderDetail orderDetailDtotoOrderDetailEntity(OrderDetail orderDetailDto);
}
