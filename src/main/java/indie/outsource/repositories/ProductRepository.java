package indie.outsource.repositories;

import indie.outsource.model.ProductWithInfo;

public interface ProductRepository extends Repository<ProductWithInfo> {
    boolean decreaseProductQuantity(ProductWithInfo product, int quantity);
    boolean increaseProductQuantity(ProductWithInfo product, int quantity);
}