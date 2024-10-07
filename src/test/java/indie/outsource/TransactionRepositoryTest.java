package indie.outsource;

import indie.outsource.model.Client;
import indie.outsource.model.Transaction;
import indie.outsource.model.products.Tree;
import indie.outsource.repositories.TransactionRelationalRepository;
import indie.outsource.repositories.TransactionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

public class TransactionRepositoryTest {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

//    @BeforeEach
//    void init() {
//        EntityManager em = emf.createEntityManager();
//
//        em.getTransaction().begin();
//        em.createNativeQuery("DELETE FROM TABLE shop_transaction").executeUpdate();
//        em.createNativeQuery("TRUNCATE TABLE client RESTART IDENTITY").executeUpdate();
//        em.createNativeQuery("TRUNCATE TABLE product_with_info RESTART IDENTITY").executeUpdate();
//        em.getTransaction().commit();
//
//        em.getTransaction().begin();
//        Client client1 = new Client("John","Doe","6th Street");
//        em.persist(client1);
//        em.getTransaction().commit();
//
//        em.getTransaction().begin();
//        Tree tree = new Tree();
//        tree.setHeight(20);
//        tree.setName("wasd");
//        tree.setPrice(20);
//        tree.setGrowthStage(2);
//        em.persist(tree);
//        em.getTransaction().commit();
//
//        em.getTransaction().begin();
//        Transaction transaction = new Transaction();
//        transaction.setClient(client1);
//        transaction.setId(5);
//        transaction.addProduct(tree,5,20);
//        em.persist(transaction);
//        em.getTransaction().commit();
//    }

    @Test
    void testAdd() {
        EntityManager em = emf.createEntityManager();
        TransactionRepository repository = new TransactionRelationalRepository(em);
        assertEquals(repository.getAll().size(), 0);

        em.getTransaction().begin();
        Client client1 = new Client("John2","Doe2","6th Street");
        em.persist(client1);
        em.getTransaction().commit();

        Transaction transaction = new Transaction();
        transaction.setClient(client1);

        repository.add(transaction);
        assertEquals(repository.getAll().size(), 1);
        assertEquals(repository.getAll().getFirst().getClient().getName(), client1.getName());
    }

    @Test
    void testRemove(){
        EntityManager em = emf.createEntityManager();
        TransactionRepository repository = new TransactionRelationalRepository(em);
        repository.remove(repository.getAll().getFirst());
        assertEquals(repository.getAll().size(), 0);
    }


    void testGetById(){
        EntityManager em = emf.createEntityManager();
        TransactionRepository repository = new TransactionRelationalRepository(em);
        Transaction transaction = repository.getById(5);
        assertNotNull(transaction);
        assertEquals(transaction.getItems().getFirst().getProduct().getName(), "wasd");

    }
}
