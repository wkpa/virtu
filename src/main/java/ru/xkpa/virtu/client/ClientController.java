package ru.xkpa.virtu.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import ru.xkpa.virtu.common.VirtuException;
import ru.xkpa.virtu.common.VirtuResponse;
import ru.xkpa.virtu.common.VirtuResponseStatus;

/**
 * @author Pavel Kurakin
 */
@RestController
@RequestMapping(value = "/api/clients")
public class ClientController {

    private static final Logger LOG = LoggerFactory.getLogger(ClientController.class);

    private ClientRepository clientRepository;

    @Autowired
    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public VirtuResponse<List<Client>> getClients(@RequestParam(value = "name", defaultValue = "") String name,
                                                  @RequestParam(value = "surname", defaultValue = "") String surname,
                                                  @RequestParam(value = "patronymic", defaultValue = "") String patronymic) {
        String findName = (name != null && !name.isEmpty()) ? "%" + name.toLowerCase() + "%" : null;
        String findSurname = (surname != null && !surname.isEmpty()) ? "%" + surname.toLowerCase() + "%" : null;
        String findPatronymic = (patronymic != null && !patronymic.isEmpty()) ? "%" + patronymic.toLowerCase() + "%" : null;
        return new VirtuResponse<>(clientRepository.findClientByNames(findName, findSurname, findPatronymic));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public VirtuResponse getClient(@PathVariable Long id) {
        LOG.debug("Request client: ID={}", id);
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (!optionalClient.isPresent()) {
            LOG.debug("Request client: ID={} is not found", id);
            return new VirtuResponse<>(VirtuResponseStatus.ERROR, "Client not found");
        }
        return new VirtuResponse<>(optionalClient.get());

    }

    @RequestMapping(method = RequestMethod.POST)
    public VirtuResponse addClient(@Valid @RequestBody Client client) {
        try {
            client = clientRepository.saveAndFlush(client);
        } catch (Exception e) {
            LOG.debug("Error of add client={}", client.getFullName());
            throw new VirtuException(VirtuResponseStatus.ERROR, e.getMessage());
        }
        LOG.debug("New client: FULL_NAME={} has been added", client.getFullName());
        return new VirtuResponse<>(client);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public VirtuResponse updateClient(@PathVariable Long id, @Valid @RequestBody Client client) {
        if (!clientRepository.existsById(id)) {
            return new VirtuResponse<>(VirtuResponseStatus.ERROR, "Client not found");
        }
        try {
            client = clientRepository.saveAndFlush(client);
        } catch (Exception e) {
            LOG.debug("Error of update client={}", client.getFullName());
            throw new VirtuException(VirtuResponseStatus.ERROR, e.getMessage());
        }
        LOG.debug("New client: FULL_NAME={} has been updated", client.getFullName());
        return new VirtuResponse<>(client);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public VirtuResponse deleteClient(@PathVariable Long id) {
        if (!clientRepository.existsById(id)) {
            return new VirtuResponse<>(VirtuResponseStatus.ERROR, "Client not found");
        }
        clientRepository.deleteById(id);
        LOG.debug("Client: ID={} has been deleted", id);
        return new VirtuResponse<>("Client has been deleted");
    }

}
