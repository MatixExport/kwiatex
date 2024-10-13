package indie.outsource.managers;

import indie.outsource.model.Client;
import indie.outsource.repositories.ClientRelationalRepository;
import indie.outsource.repositories.ClientRepository;
import jakarta.persistence.EntityManagerFactory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClientManager extends Manager {
    public ClientManager(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    public List<Client> findAllClients() {
        ClientRepository clientRepository = new ClientRelationalRepository(getEntityManager());
        return clientRepository.findAll();
    }

    public boolean register(Client client) {
        try{
            inSession((entityManager) -> {
                        ClientRepository clientRepository = new ClientRelationalRepository(entityManager);
                        clientRepository.add(client);
                    }
            );
        }
        catch(Exception e){
            return false;
        }
        return true;
    }

}
