package indie.outsource;

import indie.outsource.factories.RandomDataFactory;
import indie.outsource.managers.ProductManager;
import indie.outsource.model.ProductWithInfo;
import indie.outsource.model.products.Flower;
import indie.outsource.model.products.GrassesSeeds;
import indie.outsource.model.products.Tree;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;

@Ignore
class DbInheritanceTest {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    ProductManager manager;

    @BeforeEach
    public void setUpClass() {
        manager = new ProductManager(emf);
    }

    @AfterEach
    public void tearDown() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("delete from ProductWithInfo ").executeUpdate();
        em.getTransaction().commit();
    }


    @Test
    public void testSelectTime(){

        long start = System.currentTimeMillis();
        for (int i = 0; i < 200; i++) {
            ProductWithInfo productWithInfo = RandomDataFactory.getRandomProductWithInfo();
            productWithInfo.setProduct(RandomDataFactory.getRandomProductOfClassType(GrassesSeeds.class));
            Assertions.assertTrue(manager.addProduct(productWithInfo));
        }
        for (int i = 0; i < 200; i++) {
            ProductWithInfo productWithInfo = RandomDataFactory.getRandomProductWithInfo();
            productWithInfo.setProduct(RandomDataFactory.getRandomProductOfClassType(Tree.class));
            Assertions.assertTrue(manager.addProduct(productWithInfo));
        }
        for (int i = 0; i < 200; i++) {
            ProductWithInfo productWithInfo = RandomDataFactory.getRandomProductWithInfo();
            productWithInfo.setProduct(RandomDataFactory.getRandomProductOfClassType(Flower.class));
            Assertions.assertTrue(manager.addProduct(productWithInfo));
        }
        long end = System.currentTimeMillis();
        System.out.println("DEBUG: INSERT took " + (end - start) + " MilliSeconds");
        start = System.currentTimeMillis();
        manager.findAllProducts();
        end = System.currentTimeMillis();
        System.out.println("DEBUG: Select took " + (end - start) + " MilliSeconds");
    }
}
