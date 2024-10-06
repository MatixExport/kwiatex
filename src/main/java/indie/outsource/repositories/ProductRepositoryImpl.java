package indie.outsource.repositories;

import indie.outsource.model.ProductWithInfo;
import indie.outsource.model.products.Product;
import jakarta.persistence.EntityManager;

public class ProductRepositoryImpl extends RepositoryImpl<ProductWithInfo> implements ProductRepository {
    public ProductRepositoryImpl(EntityManager em) {
        super(ProductWithInfo.class, em);
    }

    @Override
    public ProductWithInfo add(ProductWithInfo productWithInfo) {
            if (getById(productWithInfo.getId()) == null) {
                em.persist(productWithInfo);
            } else {
                productWithInfo = em.merge(productWithInfo);
            }
            return productWithInfo;
    }


}
