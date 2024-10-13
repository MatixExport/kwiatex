package indie.outsource.repositories;

import indie.outsource.model.AbstractEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.AllArgsConstructor;

import java.util.List;


@AllArgsConstructor
public abstract class RelationalRepository<T extends AbstractEntity> implements Repository<T> {

    Class<T> classType;
    EntityManager em;


    @Override
    public List<T> findAll() {
        CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(classType);
        criteriaQuery.from(classType);
        return  em.createQuery(criteriaQuery).getResultList();
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
