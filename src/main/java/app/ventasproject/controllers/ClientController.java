package app.ventasproject.controllers;

import app.ventasproject.dtos.client.ClientDto;
import app.ventasproject.dtos.client.ClientToSaveDto;
import app.ventasproject.exceptions.NotAbleToDeleteException;
import app.ventasproject.exceptions.NotFoundException;
import app.ventasproject.services.serviceInterface.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/customers")
public class ClientController {
    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping()
    public ResponseEntity<ClientDto> saveClient(@RequestBody ClientToSaveDto clientSaveDto){
        ClientDto clientCreated = clientService.saveClient(clientSaveDto);
        logger.info(clientCreated.toString());
        return ResponseEntity.ok().body(clientCreated);
    }

    @GetMapping()
    public ResponseEntity<List<ClientDto>> getClients(){
        List<ClientDto> clientDtoList = clientService.getAllClient();
        clientDtoList.forEach((clientDto -> logger.info(clientDto.toString())));
        return ResponseEntity.ok().body(clientDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable("id") long id){
        try {
            ClientDto clientDto = clientService.searchClientById(id);
            return ResponseEntity.ok().body(clientDto);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> updateClient(@PathVariable("id") Long id, @RequestBody ClientToSaveDto clientSaveDto){
        try {
            ClientDto clientUpdate = clientService.updateClient(id, clientSaveDto);
            return ResponseEntity.ok().body(clientUpdate);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<ClientDto> getClientByEmail(@PathVariable("email") String email){
        try {
            ClientDto clientDto = clientService.searchClientByEmail(email);
            return ResponseEntity.ok().body(clientDto);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/city")
    public ResponseEntity<List<ClientDto>> getClientByAddressCity(@RequestParam("city") String city){
        try {
            List<ClientDto> clientDto = clientService.searchClientByAddressCity(city);
            return ResponseEntity.ok().body(clientDto);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity<String> deleteClient(@PathVariable("id") Long id){
        try {
            clientService.deleteClient(id);
            return ResponseEntity.ok().body("eliminado.");
        } catch (NotAbleToDeleteException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
