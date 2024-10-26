package indie.outsource.repositories;

import indie.outsource.model.ProductInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;

public class ProductRelationalRepository extends RelationalRepository<ProductInfo> implements ProductRepository {
    public ProductRelationalRepository(EntityManager em) {
        super(ProductInfo.class, em);
    }

    @Override
    public boolean decreaseProductQuantity(ProductInfo product, int quantity) {
        ProductInfo currentProduct = em.find(ProductInfo.class, product.getId(), LockModeType.PESSIMISTIC_WRITE);
        if(currentProduct.getQuantity() < quantity) {
            return false;
        }
        currentProduct.setQuantity(currentProduct.getQuantity() - quantity);
        em.merge(currentProduct);
        return true;
    }

    @Override
    public void increaseProductQuantity(ProductInfo product, int quantity) {
        ProductInfo currentProduct = em.find(ProductInfo.class, product.getId(),
                LockModeType.PESSIMISTIC_WRITE);
        currentProduct.setQuantity(currentProduct.getQuantity() + quantity);

    }
}
