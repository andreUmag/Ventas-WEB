package app.ventasproject.repositoryIntegrationTest;

import app.ventasproject.models.Client;
import app.ventasproject.repositorys.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
public class ClientRepositoryTest {
    ClientRepository clientRepository;

    @Autowired
    public ClientRepositoryTest(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    @BeforeEach
    void setUp(){
        clientRepository.deleteAll();
    }
    @Test
    void findClientByEmail(){
        Client client1 = Client.builder()
                .name("len")
                .email("ls@umg.com")
                .address("P.sherman calle wallaby 42 Sydney").build();

        clientRepository.save(client1);

        Client foundClient = clientRepository.findByEmail("ls@umg.com");

        assertNotNull(foundClient.getId());
        assertEquals("ls@umg.com", foundClient.getEmail());
    }

    @Test
    void findClientByAddress(){
        Client client1 = Client.builder()
                .name("len")
                .email("ls@umg.com")
                .address("P.sherman calle wallaby 42 Sydney").build();

        clientRepository.save(client1);

        List<Client> foundClient = clientRepository.findClientByAddress("P.sherman calle wallaby 42 Sydney");

        assertEquals(1,foundClient.size());
        assertEquals("P.sherman calle wallaby 42 Sydney", foundClient.get(0).getAddress());
    }

    @Test
    void findClientsByStartName(){
        Client client1 = Client.builder()
                .name("len")
                .email("ls@umg.com")
                .address("P.sherman calle wallaby 42 Sydney").build();
        clientRepository.save(client1);

        Client client2 = Client.builder()
                .name("and")
                .email("am@umg.com")
                .address("calle 13").build();
        clientRepository.save(client2);

        List<Client> foundClients = clientRepository.NameClientFind("len");

        assertEquals(1,foundClients.size());
        assertEquals("len", foundClients.get(0).getName());
    }

    @Test
    void deleteClientbyID(){
        Client client1 = Client.builder()
                .id(1L)
                .name("len")
                .email("ls@umg.com")
                .address("P.sherman calle wallaby 42 Sydney").build();
        clientRepository.save(client1);

        clientRepository.deleteById(1L);

        assertFalse(clientRepository.existsById(1L));
    }

    @Test
    void updateEmailClientbyID(){
        Client client1 = Client.builder()
                .id(1L)
                .name("len")
                .email("ls@umg.com")
                .address("P.sherman calle wallaby 42 Sydney").build();
        clientRepository.save(client1);

        clientRepository.findById(1L).get().setEmail("ls21@umg.com");

        assertEquals("ls21@umg.com",clientRepository.findById(1L).get().getEmail());
        assertEquals("len",clientRepository.findById(1L).get().getName());
    }
}
