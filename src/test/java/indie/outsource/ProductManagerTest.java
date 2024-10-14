package indie.outsource;

import indie.outsource.factories.RandomDataFactory;
import indie.outsource.managers.ClientManager;
import indie.outsource.managers.ProductManager;
import indie.outsource.model.Client;
import indie.outsource.model.ProductWithInfo;
import indie.outsource.model.Transaction;
import indie.outsource.model.products.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductManagerTest {

    private ProductManager productManager;
    private ClientManager clientManager;

    @BeforeEach
    public void setUpClass() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        productManager = new ProductManager(emf);
        clientManager = new ClientManager(emf);
    }

    @AfterEach
    public void tearDown() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("delete from Client ").executeUpdate();
        em.createQuery("delete from ProductWithInfo ").executeUpdate();
        em.createQuery("delete from Transaction ").executeUpdate();
        em.createQuery("delete from TransactionItem ").executeUpdate();
        em.getTransaction().commit();
        emf.close();
    }

    @Test
    public void testCreateProduct() {
        ProductWithInfo productWithInfo = RandomDataFactory.getRandomProductWithInfo();

        assertTrue(productManager.addProduct(productWithInfo));

        ProductWithInfo dbProductWithInfo = productManager.findAllProducts().getFirst();
        assertEquals(productWithInfo.getId(), dbProductWithInfo.getId());
        assertEquals(productWithInfo.getProduct().getName(), dbProductWithInfo.getProduct().getName());
        assertEquals(productWithInfo.getProduct().getPrice(), dbProductWithInfo.getProduct().getPrice());
        assertEquals(productWithInfo.getQuantity(), dbProductWithInfo.getQuantity());
    }

    @Test
    public void testFinalizeTransaction() {
        Transaction transaction = new Transaction();
        Transaction transaction2 = new Transaction();

        ProductWithInfo productWithInfo = RandomDataFactory.getRandomProductWithInfo();
        productWithInfo.setQuantity(10);
        Product tree = productWithInfo.getProduct();

        assertTrue(productManager.addProduct(productWithInfo));

        transaction.addProduct(tree, 6, 6* tree.calculateSellingPrice());
        transaction2.addProduct(tree, 6, 6* tree.calculateSellingPrice());

        Client client = RandomDataFactory.getRandomClient();

        assertTrue(clientManager.register(client));
        transaction.setClient(client);
        transaction2.setClient(client);

        assertTrue(productManager.finalizeTransaction(transaction));
        assertEquals(4, productManager.findAllProducts().getFirst().getQuantity());
        assertFalse(productManager.finalizeTransaction(transaction2));

        assertTrue(productManager.increaseProductQuantity(productWithInfo, 10));
        assertEquals(14, productManager.findAllProducts().getFirst().getQuantity());
        assertTrue(productManager.finalizeTransaction(transaction2));
    }
}
