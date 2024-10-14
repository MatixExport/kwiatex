package indie.outsource;

import indie.outsource.factories.RandomDataFactory;
import indie.outsource.managers.ClientManager;
import indie.outsource.model.Client;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClientManagerTest {

    private ClientManager clientManager;


    @BeforeEach
    public void setUpClass() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        clientManager = new ClientManager(emf);
    }

    @Test
    public void testCreateClient() {
        Client client = RandomDataFactory.getRandomClient();

        assertTrue(clientManager.register(client));

        Client dbClient = clientManager.findAllClients().getFirst();
        assertEquals(client.getId(), dbClient.getId());
        assertEquals(client.getName(), dbClient.getName());
        assertEquals(client.getAddress(), dbClient.getAddress());
        assertEquals(client.getTransactions().size(), dbClient.getTransactions().size());
    }
}
