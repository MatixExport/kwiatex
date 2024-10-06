package indie.outsource;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import indie.outsource.model.Client;


public class TestTest {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");


    @Test
    void testAdd() {
        assertEquals(5, 5);
    }

    @Test
    void testDb(){
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Client client1 = new Client("John","Doe","6th Street");
        em.persist(client1);
        em.getTransaction().commit();

        em.getTransaction().begin();
        Client client = em.find(Client.class, client1.getId());
        em.getTransaction().commit();
        assertEquals("John Doe", client.getName()+" "+client.getSurname());



    }
}
