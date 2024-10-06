package indie.outsource.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.hibernate.Session;

import java.lang.reflect.Type;
import java.util.List;

@AllArgsConstructor
public abstract class RepositoryImpl<T> implements Repository<T> {

    Class<T> classType;
    EntityManager em;

    @Override
    public List<T> getAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(classType);
        Root<T> root = cq.from(classType);
        cq.select(root);
        return em.createQuery(cq).getResultList();
    }

    @Override
    public T getById(int id) {
        return classType.cast(em.find(classType,id));
    }

    @Override
    public void remove(T t) {
        em.getTransaction().begin();
        System.out.println("Halo1");
        if (t != null) {
            System.out.println("Halo");
            em.remove(t);
        }
//        if(em.contains(t)) {
//            em.remove(t);
//        }else {
//            em.merge(t);
//            System.out.println("Merging");
//        }
        em.getTransaction().commit();
    }
}
