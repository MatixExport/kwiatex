package indie.outsource.managers;

import indie.outsource.model.Client;
import indie.outsource.repositories.ClientRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClientManager {

    private ClientRepository clientRepository;            // needs to be injected

    public Client getClientById(int id) {
        return clientRepository.getById(id);
    }

    public Client registerClient(String firstName, String lastName, String address ){
        return clientRepository.add(new Client(firstName,lastName,address));
    }
}
