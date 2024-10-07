package indie.outsource.repositories;

import indie.outsource.model.Client;
import jakarta.persistence.EntityManager;

public class ClientRelationalRepository extends RelationalRepository<Client> implements ClientRepository {

    public ClientRelationalRepository(EntityManager em) {
        super(Client.class, em);
    }
}
