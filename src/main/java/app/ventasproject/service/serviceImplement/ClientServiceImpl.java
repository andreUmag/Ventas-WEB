package app.ventasproject.service.serviceImplement;

import app.ventasproject.dto.client.ClientDto;
import app.ventasproject.dto.client.ClientMapper;
import app.ventasproject.dto.client.ClientToSaveDto;
import app.ventasproject.exception.NotFoundException;
import app.ventasproject.models.Client;
import app.ventasproject.repositorys.ClientRepository;
import app.ventasproject.service.serviceInterface.ClientService;

import java.util.List;
import java.util.Objects;

public class ClientServiceImpl implements ClientService {
    private final ClientMapper clientMapper;
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientMapper clientMapper, ClientRepository clientRepository) {
        this.clientMapper = clientMapper;
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientDto saveClient(ClientToSaveDto clientDto) {
        Client client = ClientMapper.INSTANCE.clientToSaveDToToEntity(clientDto);
        Client clienSaved = clientRepository.save(client);
        return clientMapper.clientEntitytoClientDto(clienSaved);
    }

    @Override
    public ClientDto updateClient(Long id, ClientToSaveDto client) {
        return null;
    }

    @Override
    public ClientDto searchClientById(Long id) throws NotFoundException {
        Client client = clientRepository.findById(id).orElseThrow(()-> new NotFoundException("No encontrado"));
        return clientMapper.clientEntitytoClientDto(client);
    }

    @Override
    public List<ClientDto> getAllClient() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map(client -> clientMapper.clientEntitytoClientDto(client))
                .toList();
    }

    @Override
    public ClientDto searchClientByEmail(String email) throws NotFoundException {
        Client client = clientRepository.findByEmail(email);
        if(Objects.isNull(client))
            throw new NotFoundException("Usuario no encontrado");
        return clientMapper.clientEntitytoClientDto(client);
    }

    @Override
    public ClientDto searchClientByAddresCity(String addres) throws NotFoundException {
        List<Client> clients = clientRepository.findClientByAddress(addres);
        if(clients.isEmpty()) {
            throw new NotFoundException("Usuario no encontrado");
        }
        return clientMapper.clientEntitytoClientDto(clients.get(0));
    }

    @Override
    //falta agregar NotAbleToDeleteException
    public void removeClient(Long id) {
        Client client = clientRepository.findById(id).orElseThrow();
        clientRepository.delete(client);
    }
}
