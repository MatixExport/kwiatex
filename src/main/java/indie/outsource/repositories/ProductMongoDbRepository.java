package indie.outsource.repositories;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import indie.outsource.model.ProductWithInfo;
import org.bson.conversions.Bson;

public class ProductMongoDbRepository extends MongoDbRepository<ProductWithInfo> implements ProductRepository {

    public ProductMongoDbRepository(MongoDatabase db) {
        super(ProductWithInfo.class, db);
    }

    @Override
    public void decreaseProductQuantity(ProductWithInfo product, int quantity) {
        Bson filter = Filters.eq("_id", product.getId());
        Bson update = Updates.inc("productInfo.quantity", -1*quantity);
        collection.updateOne(filter, update);
    }

    @Override
    public void increaseProductQuantity(ProductWithInfo product, int quantity) {
        Bson filter = Filters.eq("_id", product.getId());
        Bson update = Updates.inc("productInfo.quantity", quantity);
        collection.updateOne(filter, update);
    }
}
