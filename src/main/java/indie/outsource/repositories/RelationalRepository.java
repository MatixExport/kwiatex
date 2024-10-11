package indie.outsource.repositories;

import indie.outsource.exceptions.DatabaseException;
import indie.outsource.model.AbstractEntity;
import indie.outsource.model.ProductWithInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.dialect.lock.LockingStrategyException;
import org.hibernate.exception.LockAcquisitionException;

import java.util.List;


@AllArgsConstructor
public abstract class RelationalRepository<T extends AbstractEntity> implements Repository<T> {

    Class<T> classType;
    EntityManager em;


    @Override
    public List<T> getAll() {
        CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(classType);
        criteriaQuery.from(classType);
        return em.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public T getById(int id) {
        return classType.cast(em.find(classType,id));
    }



    @Override
    public T add(T t) {
        if (t.getEntityId() == null) {
            em.persist(t);
        } else {
            t = em.merge(t);
        }
        return t;


    }

    @Override
    public void remove(T t) {
        if(em.contains(t)) {
            em.remove(t);
        }else {
            em.merge(t);
        }
    }
}
