package app.ventasproject.service.serviceInterface;
import app.ventasproject.dto.client.ClientDto;
import app.ventasproject.dto.client.ClientToSaveDto;
import app.ventasproject.exception.NotFoundException;
import java.util.List;

public interface ClientService {
    ClientDto saveClient(ClientToSaveDto client);
    ClientDto updateClient(Long id, ClientToSaveDto client);
    ClientDto searchClientById(Long id) throws NotFoundException;
    List<ClientDto> getAllClient();
    ClientDto searchClientByEmail(String email) throws NotFoundException;
    ClientDto searchClientByAddressCity(String addres) throws NotFoundException;
    void removeClient(Long id);
}
