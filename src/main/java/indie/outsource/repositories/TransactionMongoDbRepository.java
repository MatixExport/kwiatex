package indie.outsource.repositories;

import com.mongodb.client.MongoDatabase;
import indie.outsource.model.ShopTransaction;

public class TransactionMongoDbRepository extends MongoDbRepository<ShopTransaction> implements TransactionRepository {

    public TransactionMongoDbRepository(MongoDatabase db) {
        super(ShopTransaction.class, db);
    }
}
