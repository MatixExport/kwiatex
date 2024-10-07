package indie.outsource.repositories;

import indie.outsource.model.ProductWithInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PessimisticLockException;

public class ProductRelationalRepository extends RelationalRepository<ProductWithInfo> implements ProductRepository {
    public ProductRelationalRepository(EntityManager em) {
        super(ProductWithInfo.class, em);
    }

//    @Override
//    public boolean decreaseProductQuantity(ProductWithInfo product, int quantity) {
//        em.getTransaction().begin();
//        ProductWithInfo currentProduct = em.find(ProductWithInfo.class, product.getId(),
//                LockModeType.OPTIMISTIC);
//        if(currentProduct.getQuantity() < quantity) {
//            return false;
//        }
//        currentProduct.setQuantity(currentProduct.getQuantity() - quantity);
//        em.merge(currentProduct);
//        try{
//            em.getTransaction().commit();
//        }
//        catch(OptimisticLockException e){
//            em.getTransaction().rollback();
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public boolean increaseProductQuantity(ProductWithInfo product, int quantity) {
//        em.getTransaction().begin();
//        ProductWithInfo currentProduct = em.find(ProductWithInfo.class, product.getId(),
//                LockModeType.OPTIMISTIC);
//        currentProduct.setQuantity(currentProduct.getQuantity() + quantity);
//        try{
//            em.getTransaction().commit();
//        }
//        catch(OptimisticLockException e){
//            em.getTransaction().rollback();
//            return false;
//        }
//        return true;
//
//    }

@Override
public boolean decreaseProductQuantity(ProductWithInfo product, int quantity) {
    em.getTransaction().begin();
    ProductWithInfo currentProduct = em.find(ProductWithInfo.class, product.getId(),
            LockModeType.PESSIMISTIC_WRITE);
    if(currentProduct.getQuantity() < quantity) {
        return false;
    }
    currentProduct.setQuantity(currentProduct.getQuantity() - quantity);
    em.merge(currentProduct);
    try{
        em.getTransaction().commit();
    }
    catch(PessimisticLockException e){
        em.getTransaction().rollback();
        return false;
    }
    return true;
}

    @Override
    public boolean increaseProductQuantity(ProductWithInfo product, int quantity) {
        em.getTransaction().begin();
        ProductWithInfo currentProduct = em.find(ProductWithInfo.class, product.getId(),
                LockModeType.PESSIMISTIC_WRITE);
        currentProduct.setQuantity(currentProduct.getQuantity() + quantity);
        try{
            em.getTransaction().commit();
        }
        catch(PessimisticLockException e){
            em.getTransaction().rollback();
            return false;
        }
        return true;

    }
}
