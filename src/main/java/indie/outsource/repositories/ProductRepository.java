package indie.outsource.repositories;

import indie.outsource.model.ProductInfo;

public interface ProductRepository extends Repository<ProductInfo> {
    boolean decreaseProductQuantity(ProductInfo product, int quantity);
    void increaseProductQuantity(ProductInfo product, int quantity);
}