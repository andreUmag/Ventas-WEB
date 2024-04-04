package app.ventasproject.controllers;

import app.ventasproject.dtos.client.ClientDto;
import app.ventasproject.dtos.client.ClientMapper;
import app.ventasproject.dtos.client.ClientToSaveDto;
import app.ventasproject.dtos.order.OrderDto;
import app.ventasproject.dtos.orderDetail.OrderDetailToSaveDto;
import app.ventasproject.models.Client;
import app.ventasproject.models.Order;
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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    ClientDto clientDto, clientDto2;

    @BeforeEach
    void setUp() {
        List<Order> ordersList = new ArrayList<>();
        client = Client.builder()
                .id(1L)
                .name("Andres Martinez")
                .email("correo@gmail.com")
                .address("Mi casa barranquilla")
                .orders(ordersList)
                .build();

        client2 = Client.builder()
                .id(2L)
                .name("Lenis Estevez")
                .email("correo2@gmail.com")
                .address("Mi casa Santa Marta")
                .build();

        clientDto = ClientMapper.INSTANCE.clientEntitytoClientDto(client);
        clientDto2 = ClientMapper.INSTANCE.clientEntitytoClientDto(client2);
    }

    @Test
    void testSaveClient() throws Exception {
        ClientToSaveDto clientToSaveDto = new ClientToSaveDto(
                1L,
                "Andres Martinez",
                "correo@gmail.com",
                "barranquilla"
        );
        when(clientService.saveClient(clientToSaveDto)).thenReturn(clientDto);

        String json = objectMapper.writeValueAsString(clientToSaveDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isOk());
    }

    @Test
    void testGetClients() throws Exception {
        List<ClientDto> clientDtoList = new ArrayList<>();
        clientDtoList.add(clientDto);

        when(clientService.getAllClient()).thenReturn(clientDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetClientById() throws Exception {
        long clientId = 1L;

        ClientDto clientDto = ClientMapper.INSTANCE.clientEntitytoClientDto(client);

        when(clientService.searchClientById(clientId)).thenReturn(clientDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/{id}", clientId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(clientService, times(1)).searchClientById(clientId);
        verifyNoMoreInteractions(clientService);
    }

    @Test
    void testUpdateClient() throws Exception {
        ClientToSaveDto clientToSaveDto = new ClientToSaveDto(client.getId(),
                client.getName(), client.getEmail(), client.getAddress());

        when(clientService.updateClient(client.getId(), clientToSaveDto)).thenReturn(clientDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/customers/{id}", client.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDto)))
                .andExpect(status().isOk());

        verify(clientService, times(1)).updateClient(client.getId(), clientToSaveDto);
    }

    @Test
    void testGetClientByEmail() throws Exception {
        String email = "correo@gmail.com";
        when(clientService.searchClientByEmail(email)).thenReturn(clientDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/email/{email}", email)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        verify(clientService, times(1)).searchClientByEmail(email);
        verifyNoMoreInteractions(clientService);
    }

    @Test
    void testGetClientByAddressCity() throws Exception {
        String city = "testCity";
        List<ClientDto> clientDtoList = new ArrayList<>();
        clientDtoList.add(clientDto);

        when(clientService.searchClientByAddressCity(city)).thenReturn(clientDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/city").param("city", city)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(clientService, times(1)).searchClientByAddressCity(city);
        verifyNoMoreInteractions(clientService);
    }

    @Test
    void testDeleteClient() throws Exception {
        long clientId = 1L;

        doNothing().when(clientService).deleteClient(clientId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/customers/{id}", clientId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(clientService, times(1)).deleteClient(clientId); verifyNoMoreInteractions(clientService);
    }
}