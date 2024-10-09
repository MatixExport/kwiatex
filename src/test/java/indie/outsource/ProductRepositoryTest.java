package indie.outsource;

import indie.outsource.model.ProductWithInfo;
import indie.outsource.model.products.Tree;
import indie.outsource.repositories.ProductRelationalRepository;
import indie.outsource.repositories.ProductRepository;
import jakarta.persistence.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

public class ProductRepositoryTest {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

    private Tree tree;
    private ProductWithInfo productWithInfo;
    private EntityManager em;
    private EntityManager em2;
    private EntityManager em3;

    @BeforeEach
    void createModelObjects() {
        tree = new Tree();
        tree.setHeight(20);
        tree.setName("wasd");
        tree.setPrice(20);
        tree.setGrowthStage(2);

        productWithInfo = new ProductWithInfo();
        productWithInfo.setPrice(2);
        productWithInfo.setQuantity(5);
        productWithInfo.setProduct(tree);

        em = emf.createEntityManager();
        em2 = emf.createEntityManager();
        em3 = emf.createEntityManager();
    }

    @Test
    public void lockTest() throws InterruptedException {
        ProductRepository productRepository = new ProductRelationalRepository(em);
        em.getTransaction().begin();
        em.persist(tree);
        em.getTransaction().commit();

        productRepository.add(productWithInfo);
        int id = productWithInfo.getId();

        System.out.println("----------------------------------------------");

        System.out.println("trans 1");
        em.getTransaction().begin();
        System.out.println("select 1");
        ProductWithInfo target = em.find(ProductWithInfo.class, id, LockModeType.PESSIMISTIC_WRITE);
        System.out.println("update 1");
        target.setQuantity(6);


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
        }
        );

        thread.start();
        System.out.println("commit 1");
        em.getTransaction().commit();
        System.out.println("after 1");

        thread.join();

        ProductWithInfo productWithInfof = em3.find(ProductWithInfo.class, id);
        assertEquals(7, productWithInfof.getQuantity());


    }
}
