package indie.outsource;

import indie.outsource.managers.ProductManager;
import indie.outsource.model.ProductWithInfo;
import indie.outsource.model.products.Product;
import indie.outsource.model.products.Tree;
import jakarta.persistence.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductRepositoryTest {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    private final EntityManagerFactory emf1 = Persistence.createEntityManagerFactory("default");
    private final EntityManagerFactory emf2 = Persistence.createEntityManagerFactory("default");

    private EntityManager em;
    private EntityManager em2;
    private EntityManager em3;

    @BeforeEach
    void createModelObjects() {
        Tree tree = new Tree();
        tree.setHeight(20);
        tree.setName("wasd");
        tree.setPrice(20);
        tree.setGrowthStage(2);

        ProductWithInfo productWithInfo = new ProductWithInfo();
        productWithInfo.setPrice(2);
        productWithInfo.setQuantity(5);
        productWithInfo.setProduct(tree);

        em = emf.createEntityManager();
        em2 = emf1.createEntityManager();
        em3 = emf2.createEntityManager();
    }

    @Test
    public void lockTest() throws InterruptedException {
        ProductManager productManager = new ProductManager(emf);
        Product tree = new Tree();
        tree.setName("dab");
        tree.setPrice(120);

        ProductWithInfo productWithInfo = new ProductWithInfo();
        productWithInfo.setProduct(tree);
        productWithInfo.setQuantity(10);
        assertTrue(productManager.addProduct(productWithInfo));

        int id = productWithInfo.getId();

        System.out.println("----------------------------------------------");

        System.out.println("trans 1");
        em.getTransaction().begin();
        System.out.println("select 1");
        ProductWithInfo target = em.find(ProductWithInfo.class, id, LockModeType.PESSIMISTIC_WRITE);
        System.out.println("update 1");
        target.setQuantity(target.getQuantity() + 1);


        Thread thread = new Thread(() -> {
            System.out.println("trans 2");
            em2.getTransaction().begin();
            System.out.println("select 2");
            ProductWithInfo target1 = em2.find(ProductWithInfo.class, id, LockModeType.PESSIMISTIC_WRITE);
            System.out.println("update 2");
            target1.setQuantity(target1.getQuantity() + 1);
            System.out.println("commit 2");
            em2.getTransaction().commit();
            System.out.println("after 2");
        });

        thread.start();
        System.out.println("commit 1");
        em.getTransaction().commit();
        System.out.println("after 1");

        thread.join();

        ProductWithInfo productWithInfof = em3.find(ProductWithInfo.class, id);
        assertEquals(12, productWithInfof.getQuantity());
    }
}
