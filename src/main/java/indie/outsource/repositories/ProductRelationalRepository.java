package indie.outsource.repositories;

import indie.outsource.model.ProductWithInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;

public class ProductRelationalRepository extends RelationalRepository<ProductWithInfo> implements ProductRepository {
    public ProductRelationalRepository(EntityManager em) {
        super(ProductWithInfo.class, em);
    }

    @Override
    public boolean decreaseProductQuantity(ProductWithInfo product, int quantity) {
        ProductWithInfo currentProduct = em.find(ProductWithInfo.class, product.getId(), LockModeType.PESSIMISTIC_WRITE);
        if(currentProduct.getQuantity() < quantity) {
            return false;
        }
        currentProduct.setQuantity(currentProduct.getQuantity() - quantity);
        em.merge(currentProduct);
        return true;
    }

    @Override
    public void increaseProductQuantity(ProductWithInfo product, int quantity) {
        ProductWithInfo currentProduct = em.find(ProductWithInfo.class, product.getId(),
                LockModeType.PESSIMISTIC_WRITE);
        currentProduct.setQuantity(currentProduct.getQuantity() + quantity);

    }
}
