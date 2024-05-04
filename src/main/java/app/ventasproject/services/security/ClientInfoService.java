package app.ventasproject.services.security;

import app.ventasproject.controllers.dto.ClientInfo;
import app.ventasproject.models.Client;
import app.ventasproject.repositories.ClientRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ClientInfoService implements UserDetailsService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public ClientInfoService(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Client> userDetail = Optional.ofNullable(clientRepository.findByEmail(email));
        return userDetail.map(ClientInfoDetail::new)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }

    public ClientInfo addUser(ClientInfo clientInfo) {
        Client client = new Client(null, clientInfo.name(), clientInfo.email(), passwordEncoder.encode(clientInfo.password()),null,clientInfo.roles(),null);
        client = clientRepository.save(client);
        return new ClientInfo(client.getName(), client.getEmail(), clientInfo.password(), client.getRole());

    }
}
