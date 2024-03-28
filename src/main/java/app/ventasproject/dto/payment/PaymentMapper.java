package app.ventasproject.dto.payment;
import app.ventasproject.models.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);
    PaymentDto paymentEntitytoOPaymentDto(Payment payment);
    Payment paymentDtotoPaymentEntity(PaymentDto paymentDto);
}
