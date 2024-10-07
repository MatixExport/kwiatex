package indie.outsource.repositories;

import indie.outsource.model.AbstractEntity;
import indie.outsource.model.ProductWithInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

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
        em.getTransaction().begin();
        if (t.getEntityId() == null) {
            em.persist(t);
        } else {
            t = em.merge(t);
        }
        em.getTransaction().commit();
        return t;


    }

//    @Transactional
    @Override
    public void remove(T t) {
        em.getTransaction().begin();
        if(em.contains(t)) {
            em.remove(t);
        }else {
            em.merge(t);
        }
        em.getTransaction().commit();
    }
}
