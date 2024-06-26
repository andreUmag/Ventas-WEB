package app.ventasproject.services.serviceImplement;

import app.ventasproject.dtos.client.ClientDto;
import app.ventasproject.dtos.client.ClientMapper;
import app.ventasproject.dtos.client.ClientToSaveDto;
import app.ventasproject.exceptions.NotFoundException;
import app.ventasproject.models.Client;
import app.ventasproject.repositories.ClientRepository;
import app.ventasproject.services.serviceInterface.ClientService;

import java.util.Collections;
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
        Client client = clientMapper.clientToSaveDToToEntity(clientDto);
        Client clienSaved = clientRepository.save(client);
        return clientMapper.clientEntitytoClientDto(clienSaved);
    }

    @Override
    public ClientDto updateClient(Long id, ClientToSaveDto clientDto) {
        return clientRepository.findById(id).map(client -> {
            client.setName(clientDto.name());
            client.setEmail(clientDto.email());
            client.setAddress(clientDto.address());
            Client clientSaved = clientRepository.save(client);

            return clientMapper.clientEntitytoClientDto(clientSaved);
        }).orElseThrow(() -> new NotFoundException("No encontrado"));
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
            throw new NotFoundException("No encontrado");
        return clientMapper.clientEntitytoClientDto(client);
    }

    @Override
    public List<ClientDto> searchClientByAddressCity(String address) throws NotFoundException {
        List<Client> clients = clientRepository.findClientByAddress(address);
        if(Objects.isNull(clients) || clients.isEmpty())
            throw new NotFoundException("No encontrado");
        return Collections.singletonList(clientMapper.clientEntitytoClientDto(clients.get(0)));
    }


    @Override
    public void deleteClient(Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null) {
            throw new NotFoundException("No encontrado");
        }
        clientRepository.delete(client);
    }
}
