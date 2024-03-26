package app.ventasproject.repositorys;

import app.ventasproject.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public class ClientRepository extends JpaRepository<Client, Long> {
}
