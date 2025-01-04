package indie.outsource;

import indie.outsource.model.Client;
import indie.outsource.repositories.cassandra.ApplicationContext;
import indie.outsource.repositories.cassandra.clients.ClientRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ClientRepositoryTest {

    ClientRepository clientRepository;

    @Before
    public void setUp() {
        ApplicationContext applicationContext = ApplicationContext.getInstance();
        clientRepository = applicationContext.getClientRepository();
    }

    @After
    public void tearDown(){
        clientRepository.truncateTables();
    }

    @Test
    public void insertTest() {
        Client client = new Client("insert", "Nazwisko", 10, "al politechniki");

        clientRepository.save(client);
        client.setId(1);
        clientRepository.save(client);

        assertEquals(client.getId(), clientRepository.findByName("insert").getFirst().getId());
        assertEquals(2, clientRepository.findByName("insert").size());
        assertEquals(client.getName(), clientRepository.findById(10).getName());
    }

    @Test
    public void deleteTest() {
        Client client = new Client("delete", "Nazwisko", 10, "al politechniki");

        clientRepository.save(client);
        clientRepository.delete(client);

        assertEquals(0, clientRepository.findByName("delete").size());
        assertNull(clientRepository.findById(10));
    }

    @Test
    public void updateTest() {
        Client client = new Client("update", "Nazwisko", 10, "al politechniki");

        clientRepository.save(client);

        client = new Client("Nowe update", "Nowe Nazwisko", 10, "al polite2chniki");

        clientRepository.update(client);

        assertEquals(client.getId(), clientRepository.findByName("Nowe update").getFirst().getId());
        assertEquals(client.getName(), clientRepository.findById(10).getName());
    }
}
