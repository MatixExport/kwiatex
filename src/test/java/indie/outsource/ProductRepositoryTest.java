package indie.outsource;

import indie.outsource.model.ProductWithInfo;
import indie.outsource.model.products.Tree;
import indie.outsource.repositories.ProductRelationalRepository;
import indie.outsource.repositories.ProductRepository;
import jakarta.persistence.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

public class ProductRepositoryTest {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

    @Test
    public void lockTest() {

        EntityManager em = emf.createEntityManager();
        EntityManager em2 = emf.createEntityManager();

        ProductRepository productRepository = new ProductRelationalRepository(em);
        em.getTransaction().begin();
        Tree tree = new Tree();
        tree.setHeight(20);
        tree.setName("wasd");
        tree.setPrice(20);
        tree.setGrowthStage(2);
        em.persist(tree);
        em.getTransaction().commit();

        ProductWithInfo productWithInfo = new ProductWithInfo();
        productWithInfo.setPrice(2);
        productWithInfo.setQuantity(5);
        productWithInfo.setProduct(tree);
        productRepository.add(productWithInfo);

        ProductWithInfo finalProductWithInfo = productWithInfo;
        CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
            return productRepository.increaseProductQuantity(finalProductWithInfo,1);
        });


        EntityTransaction t1 = em2.getTransaction();
        t1.begin();
        ProductWithInfo target = em2.find(ProductWithInfo.class, productWithInfo.getId(),LockModeType.PESSIMISTIC_WRITE);
        target.setQuantity(target.getQuantity() + 1);
        future.thenAccept(Assertions::assertTrue);
        assertDoesNotThrow(t1::commit);

        productWithInfo = em2.find(ProductWithInfo.class, productWithInfo.getId());
        assertEquals(productWithInfo.getQuantity(), 7);

    }
}
