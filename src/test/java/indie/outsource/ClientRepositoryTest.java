package indie.outsource;

import indie.outsource.model.Client;
import indie.outsource.repositories.cassandra.clients.ClientRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ClientRepositoryTest {

    ClientRepository repository;

    @Before
    public void setUp() {
        repository = new ClientRepository();
    }

    @After
    public void tearDown(){
        repository.close();
    }

    @Test
    public void insertTest() {
        Client client = new Client("Imie", "Nazwisko", 10, "al politechniki");

        repository.save(client);
        client.setId(1);
        repository.save(client);

        assertEquals(client.getId(), repository.findByName("Imie").getFirst().getId());
        assertEquals(2, repository.findByName("Imie").size());
        assertEquals(client.getName(), repository.findById(10).getName());
    }

    @Test
    public void deleteTest() {
        Client client = new Client("Imie", "Nazwisko", 10, "al politechniki");

        repository.save(client);
        repository.delete(client);

        assertNull(repository.findByName("Imie"));
        assertNull(repository.findById(10));
    }

    @Test
    public void updateTest() {
        Client client = new Client("Imie", "Nazwisko", 10, "al politechniki");

        repository.save(client);

        client = new Client("Nowe Imie", "Nowe Nazwisko", 10, "al polite2chniki");

        repository.update(client);

        assertEquals(client.getId(), repository.findByName("Nowe Imie").getFirst().getId());
        assertEquals(client.getName(), repository.findById(10).getName());
    }
}
