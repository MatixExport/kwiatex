package indie.outsource;

import indie.outsource.factories.RandomDataFactory;
import indie.outsource.model.Client;
import indie.outsource.repositories.ClientRelationalRepository;
import indie.outsource.repositories.ClientRepository;
import jakarta.persistence.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClientRepositoryTest {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    private final EntityManagerFactory emf1 = Persistence.createEntityManagerFactory("default");


    EntityManager em;
    EntityManager em1;

    ClientRepository repo;
    ClientRepository repo1;


    @BeforeEach
    public void setUpClass() {
        em = emf.createEntityManager();
        em1 = emf1.createEntityManager();
        repo = new ClientRelationalRepository(em);
        repo1 = new ClientRelationalRepository(em1);
    }

    @AfterEach
    public void tearDown() {
        em.getTransaction().begin();
        em.createQuery("delete from Client ").executeUpdate();
        em.getTransaction().commit();
    }

    @Test
    public void testOptimisticLock() {
        Client client = RandomDataFactory.getRandomClient();

        em.getTransaction().begin();
        repo.add(client);
        em.getTransaction().commit();


//        Transaction1
        em.getTransaction().begin();
        client.setName("Andrzej1");
        client.setSurname("Wasder1");
        repo.add(client);

//        Transaction2
        em1.getTransaction().begin();
        Client client1 = em1.find(Client.class, client.getId());
        client1.setName("Andrzej2");
        client1.setSurname("Wasder2");
        repo1.add(client1);
        em1.getTransaction().commit();

        assertThrows(RollbackException.class, () ->{
            em.getTransaction().commit();
        });

        Client client2 = repo.getById(client.getId());
        assertEquals(client2.getName(), "Andrzej2");
        assertEquals(client2.getSurname(), "Wasder2");
    }


}
