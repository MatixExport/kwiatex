package indie.outsource.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import indie.outsource.model.ProductInfo;
import indie.outsource.model.ProductWithInfo;

public class ProductMongoDbRepository extends MongoDbRepository<ProductWithInfo> implements ProductRepository {

    public ProductMongoDbRepository(MongoDatabase db) {
        super(ProductWithInfo.class, db);
    }

    @Override
    public boolean decreaseProductQuantity(ProductWithInfo product, int quantity) {
        return false;
    }

    @Override
    public void increaseProductQuantity(ProductWithInfo product, int quantity) {

    }
}
