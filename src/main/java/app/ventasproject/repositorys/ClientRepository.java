package app.ventasproject.repositorys;

import app.ventasproject.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findByEmail(String email);
    List<Client> findClientByAddress(String address);
    @Query("SELECT c FROM Client c WHERE c.address like %?1%")
    List<Client> findCityByAddress(String address);
    @Query("SELECT c FROM Client c WHERE c.name like ?1%")
    List<Client> NameClientFind(String name);
}
