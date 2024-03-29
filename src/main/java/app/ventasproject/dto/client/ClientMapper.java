package app.ventasproject.dto.client;
import app.ventasproject.models.Client;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);
    ClientDto clientEntitytoClientDto(Client client);
    Client clientDtotoClientEntity(ClientDto clientDto);
}