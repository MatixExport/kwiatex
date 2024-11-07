package indie.outsource.repositories;

import indie.outsource.model.ProductWithInfo;

public interface ProductRepository extends Repository<ProductWithInfo> {
    void decreaseProductQuantity(ProductWithInfo product, int quantity);
    void increaseProductQuantity(ProductWithInfo product, int quantity);
}