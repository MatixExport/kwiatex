package indie.outsource;

import indie.outsource.factories.RandomDataFactory;
import indie.outsource.model.ProductWithInfo;
import indie.outsource.model.products.Product;
import indie.outsource.model.products.Tree;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CascadeTest {


    @AfterEach
    public void tearDown() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("delete from ProductWithInfo ").executeUpdate();
        em.createQuery("delete from Product ").executeUpdate();
        em.createQuery("delete from Tree ").executeUpdate();
        em.createQuery("delete from Plant ").executeUpdate();
        em.getTransaction().commit();
        emf.close();
    }

    @Test
    public void productDetachesFromProductWithInfoOnRemoval(){
        ProductWithInfo productWithInfo = RandomDataFactory.getRandomProductWithInfo();
        Product tree = productWithInfo.getProduct();

        EntityManager em = Persistence.createEntityManagerFactory("default").createEntityManager();
        em.getTransaction().begin();
        em.persist(productWithInfo);
        assertEquals(tree.getId(), em.find(productWithInfo.getClass(), productWithInfo.getId()).getProduct().getId());
        assertEquals(tree.getId(), em.find(Tree.class, productWithInfo.getProduct().getId()).getId());

        em.remove(productWithInfo);
        em.getTransaction().commit();

        em.getTransaction().begin();
        assertEquals(tree.getProductInfo(), em.find(Product.class, tree.getId()).getProductInfo());
        assertNull(em.find(productWithInfo.getClass(), productWithInfo.getId()));
        em.getTransaction().commit();

        assertTrue(true);

    }
    @Test
    public void productWithInfoDeleteOnProductRemoval(){
        ProductWithInfo productWithInfo = RandomDataFactory.getRandomProductWithInfo();
        Product tree = productWithInfo.getProduct();

        EntityManager em = Persistence.createEntityManagerFactory("default").createEntityManager();
        em.getTransaction().begin();
        em.persist(productWithInfo);
        assertEquals(tree.getId(), em.find(productWithInfo.getClass(), productWithInfo.getId()).getProduct().getId());
        assertEquals(tree.getId(), em.find(Tree.class, productWithInfo.getProduct().getId()).getId());

        em.remove(tree);
        em.getTransaction().commit();

        em.getTransaction().begin();
        assertNull(em.find(Product.class, tree.getId()));
        assertNull(em.find(productWithInfo.getClass(), productWithInfo.getId()));
        em.getTransaction().commit();

        assertTrue(true);

    }
}
