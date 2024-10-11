package indie.outsource.managers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;

import java.util.function.Consumer;

@AllArgsConstructor
public abstract class Manager {

    private EntityManagerFactory entityManagerFactory;


    protected void inSession(Consumer<EntityManager> work) {
        var entityManager = entityManagerFactory.createEntityManager();
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            work.accept(entityManager);
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
        finally {
            entityManager.close();
        }
    }

    protected EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
