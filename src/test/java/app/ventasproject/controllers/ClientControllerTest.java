package app.ventasproject.controllers;

import app.ventasproject.dtos.client.ClientDto;
import app.ventasproject.dtos.client.ClientMapper;
import app.ventasproject.dtos.client.ClientToSaveDto;
import app.ventasproject.models.Client;
import app.ventasproject.services.serviceInterface.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@WebMvcTest(ClientController.class)
@ExtendWith(MockitoExtension.class)
class ClientControllerTest extends ModelsTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Autowired
    ObjectMapper objectMapper;

    Client client, client2;
    ClientDto clientDto;

    @BeforeEach
    void setUp() {
        Client client = Client.builder()
                .name("Andres Martinez")
                .email("correo@gmail.com")
                .address("Mi casa barranquilla")
                .build();

        Client client2 = Client.builder()
                .name("Lenis Estevez")
                .email("correo2@gmail.com")
                .address("Mi casa Santa Marta")
                .build();

        clientDto = ClientMapper.INSTANCE.clientEntitytoClientDto(client);
    }

    @Test
    void saveClient() throws Exception {
        ClientToSaveDto clientToSaveDto = new ClientToSaveDto(
                null,
                "Andres Martinez",
                "correo@gmail.com",
                "barranquilla"
        );
        when(clientService.saveClient(clientToSaveDto)).thenReturn(clientDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientToSaveDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getClients() {
    }

    @Test
    void getClientById() {
    }

    @Test
    void updateClient() {
    }

    @Test
    void getClientByEmail() {
    }

    @Test
    void getClientByAddressCity() {
    }

    @Test
    void deleteClient() {
    }
}