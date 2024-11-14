package indie.outsource.repositories;

import com.mongodb.client.MongoDatabase;
import indie.outsource.documents.ProductWithInfoDoc;
import indie.outsource.documents.ShopTransactionDoc;
import indie.outsource.documents.mappers.ProductWithInfoMapper;
import indie.outsource.documents.mappers.ShopTransactionMapper;
import indie.outsource.model.ProductWithInfo;
import indie.outsource.model.ShopTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransactionMongoDbRepository extends MongoDbRepository<ShopTransactionDoc> implements TransactionRepository {

    public TransactionMongoDbRepository(MongoDatabase db) {
        super(ShopTransactionDoc.class, db);
    }

    @Override
    public List<ShopTransaction> findAll() {
        return mongoFindAll().stream()
                .map(ShopTransactionMapper::toDomainModel)
                .toList();
    }

    @Override
    public ShopTransaction getById(UUID id) {
        return mongoGetById(id).toDomainModel();
    }

    @Override
    public ShopTransaction add(ShopTransaction shopTransaction) {
        mongoAdd(ShopTransactionMapper.fromDomainModel(shopTransaction));
        return shopTransaction;
    }

    @Override
    public void remove(ShopTransaction shopTransaction) {
        mongoRemove(new ShopTransactionDoc(shopTransaction.getId()));
    }
}
