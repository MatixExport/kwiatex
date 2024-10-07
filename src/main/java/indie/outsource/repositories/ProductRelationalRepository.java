package indie.outsource.repositories;

import indie.outsource.model.ProductWithInfo;
import jakarta.persistence.EntityManager;

public class ProductRelationalRepository extends RelationalRepository<ProductWithInfo> implements ProductRepository {
    public ProductRelationalRepository(EntityManager em) {
        super(ProductWithInfo.class, em);
    }
}
