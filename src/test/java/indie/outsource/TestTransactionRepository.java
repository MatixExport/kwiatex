package indie.outsource;

import indie.outsource.factories.RandomDataFactory;
import indie.outsource.model.Transaction;
import indie.outsource.model.products.Product;
import indie.outsource.repositories.TransactionRelationalRepository;
import indie.outsource.repositories.TransactionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import indie.outsource.model.Client;


public class TestTransactionRepository {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

    @Test
    void testAddTransactionItems(){
        EntityManager em = emf.createEntityManager();
        TransactionRepository repo = new TransactionRelationalRepository(em);

        em.getTransaction().begin();
        Client client1 = RandomDataFactory.getRandomClient();
        Product product1 = RandomDataFactory.getRandomProduct();
        em.persist(client1);
        em.persist(product1);
        em.getTransaction().commit();

        em.getTransaction().begin();
        Transaction transaction = new Transaction();
        transaction.setClient(client1);
        transaction.addProduct(product1,5,20);
        repo.add(transaction);
        em.getTransaction().commit();

        em.getTransaction().begin();
        Transaction transaction2 = repo.getById(transaction.getId());
        assertEquals(transaction2.getClient().getName(), client1.getName());
        assertEquals(transaction2.getItems().size(),1);
        assertEquals(transaction2.getItems().getFirst().getPrice(),20);
        em.getTransaction().commit();
    }
}
