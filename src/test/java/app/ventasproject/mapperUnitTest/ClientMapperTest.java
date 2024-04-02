package app.ventasproject.mapperUnitTest;

import app.ventasproject.dtos.client.ClientDto;
import app.ventasproject.dtos.client.ClientMapper;
import app.ventasproject.models.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClientMapperTest {
    Client client;
    ClientDto clientDto;
    @BeforeEach
    void setUp(){
        client = Client.builder()
                .name("len")
                .email("ls@umg.com")
                .address("P.Sherman 42")
                .build();

        clientDto = new ClientDto(
                1L,
                "len",
                "ls@umg.com" ,
                "P.Sherman 42",
                null);
    }

    @Test
    public void clientEntitytoDTO(){
        ClientDto clientDto = ClientMapper.INSTANCE.clientEntitytoClientDto(client);

        assertNotNull(clientDto);
        assertEquals(client.getName(),clientDto.name());
        assertEquals(client.getAddress(),clientDto.address());
    }

    @Test
    public void clientDTOtoEntity(){
        Client client = ClientMapper.INSTANCE.clientDtotoClientEntity(clientDto);

        assertNotNull(client);
        assertEquals(clientDto.name(),client.getName());
        assertEquals("P.Sherman 42",client.getAddress());
    }
}
