package app.ventasproject.services.serviceInterface;
import app.ventasproject.dtos.client.ClientDto;
import app.ventasproject.dtos.client.ClientToSaveDto;
import app.ventasproject.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface ClientService {
    ClientDto saveClient(ClientToSaveDto client);
    ClientDto updateClient(Long id, ClientToSaveDto client);
    ClientDto searchClientById(Long id) throws NotFoundException;
    List<ClientDto> getAllClient();
    ClientDto searchClientByEmail(String email) throws NotFoundException;
    List<ClientDto> searchClientByAddressCity(String addres) throws NotFoundException;
    void deleteClient(Long id);
}
