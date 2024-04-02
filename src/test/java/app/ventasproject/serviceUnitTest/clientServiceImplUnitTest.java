package app.ventasproject.serviceUnitTest;

import app.ventasproject.dtos.client.ClientDto;
import app.ventasproject.dtos.client.ClientMapper;
import app.ventasproject.dtos.client.ClientToSaveDto;
import app.ventasproject.exceptions.NotFoundException;
import app.ventasproject.models.Client;
import app.ventasproject.repositories.ClientRepository;
import app.ventasproject.services.serviceImplement.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class clientServiceImplUnitTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientServiceImpl clientService;
    private Client client, client2;
    private ClientDto clientDto, clientDto2;
    private ClientToSaveDto clientToSaveDto;

    @BeforeEach
    void setUp(){
        client = Client.builder()
                .id(1L)
                .name("len")
                .email("ls@umg.com")
                .address("Calle Wallaby").build();

        client2 = Client.builder().id(2L).name("and").email("adrj@umg.com").address("calle 24").build();

        clientDto = ClientMapper.INSTANCE.clientEntitytoClientDto(client);
        clientDto2 = ClientMapper.INSTANCE.clientEntitytoClientDto(client2);

        clientToSaveDto = new ClientToSaveDto(1L,"and", "adrj@umg.com", "calle 24");
    }

    @Test
    void givenUsuario_whenSaveUsuario_thenReturnUsuarioGuardado() {
        given(clientRepository.save(any())).willReturn(client);

        clientToSaveDto = new ClientToSaveDto(
                1L,
                "test",
                "test1",
                "123");

        when(clientMapper.clientEntitytoClientDto(any())).thenReturn(clientDto);

        ClientDto clientDto = clientService.saveClient(clientToSaveDto);

        assertThat(clientDto).isNotNull();
        assertThat(clientDto.id()).isEqualTo(1L);
    }

    @Test
    void givenExistingClientId_whenSearchClientById_thenReturnClientDto() throws NotFoundException {
        given(clientRepository.findById(1L)).willReturn(Optional.ofNullable(client));
        when(clientMapper.clientEntitytoClientDto(client)).thenReturn(clientDto);

        ClientDto clientFound = clientService.searchClientById(1L);

        assertThat(clientFound).isNotNull();
        assertThat(clientFound).isEqualTo(clientDto);
    }

    @Test
    void givenNonExistingClientId_whenSearchClientById_thenThrowNotFoundException() {
        given(clientRepository.findById(999L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> clientService.searchClientById(999L));
    }

    @Test
    void givenClientList_whenGetAllClients_thenReturnClientDtoList() {
        List<Client> clients = Arrays.asList(client, client2);
        List<ClientDto> clientDtoList = Arrays.asList(clientDto, clientDto2);

        given(clientRepository.findAll()).willReturn(clients);
        when(clientMapper.clientEntitytoClientDto(any())).thenAnswer(invocation -> {
            Client client = invocation.getArgument(0);
            return new ClientDto(client.getId(), client.getName(), client.getEmail(), client.getAddress(), null);
        });

        List<ClientDto> clientslist = clientService.getAllClient();

        assertThat(clientslist).isNotNull();
        assertThat(clientslist.size()).isEqualTo(clientDtoList.size());
        assertThat(clientslist).containsAll(clientDtoList);
    }

    @Test
    void searchClientByEmail_ExistingEmail_ReturnsClientDto() throws NotFoundException {
        given(clientRepository.findByEmail(client.getEmail())).willReturn(client);
        when(clientMapper.clientEntitytoClientDto(client)).thenReturn(clientDto);

        ClientDto clientFound = clientService.searchClientByEmail(client.getEmail());

        assertThat(clientFound).isNotNull();
        assertEquals(clientDto, clientFound);
    }
    @Test
    void searchClientByEmail_NonExistingEmail_ThrowsNotFoundException() {
        given(clientRepository.findByEmail("nonexistent@umg.com")).willReturn(null);
        assertThrows(NotFoundException.class, () -> clientService.searchClientByEmail("nonexistent@umg.com"));
    }

    @Test
    void searchClientByAddressCity_ExistingAddress_ReturnsClientDto() throws NotFoundException {
        List<Client> clients = new ArrayList<>();
        clients.add(client2);

        given(clientRepository.findClientByAddress(client2.getAddress())).willReturn(clients);
        when(clientMapper.clientEntitytoClientDto(client2)).thenReturn(clientDto);

        List<ClientDto> clientsFound = clientService.searchClientByAddressCity(client2.getAddress());

        assertThat(clientsFound).isNotNull();
        assertThat(clientsFound).contains(clientDto);
    }

    @Test
    void searchClientByAddressCity_NonExistingAddress_ThrowsNotFoundException() {
        given(clientRepository.findClientByAddress("Nonexistent Address")).willReturn(new ArrayList<>());
        assertThrows(NotFoundException.class, () -> clientService.searchClientByAddressCity("Nonexistent Address"));
    }

    @Test
    void removeClient_ExistingId_RemovesClient() {
        given(clientRepository.findById(1L)).willReturn(Optional.of(client));
        assertDoesNotThrow(() -> clientService.removeClient(1L));
    }

    @Test
    void removeClient_NonExistingId_ThrowsNotFoundException() {
        given(clientRepository.findById(2L)).willReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> clientService.removeClient(2L));
    }
}
