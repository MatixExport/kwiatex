package indie.outsource.repositories;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import indie.outsource.documents.ClientDoc;
import indie.outsource.documents.ProductInfoDoc;
import indie.outsource.documents.ProductWithInfoDoc;
import indie.outsource.documents.mappers.ClientMapper;
import indie.outsource.documents.mappers.ProductMapper;
import indie.outsource.documents.mappers.ProductWithInfoMapper;
import indie.outsource.documents.products.*;
import indie.outsource.model.Client;
import indie.outsource.model.ProductWithInfo;
import indie.outsource.model.products.Flower;
import indie.outsource.model.products.GrassesSeeds;
import indie.outsource.model.products.Tree;
import indie.outsource.model.products.VegetableSeeds;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductMongoDbRepository extends MongoDbRepository<ProductWithInfoDoc> implements ProductRepository {

    public ProductMongoDbRepository(MongoDatabase db) {
        super(ProductWithInfoDoc.class, db);
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

    @Override
    public List<ProductWithInfo> findAll() {
        return mongoFindAll().stream()
                .map(ProductWithInfoMapper::toDomainModel)
                .toList();
    }

    public Long getCount(){
        return mongoCount();
    }

    @Override
    public ProductWithInfo getById(UUID id) {
        return mongoGetById(id).toDomainModel();
    }

    @Override
    public ProductWithInfo add(ProductWithInfo productWithInfo) {
        mongoAdd(ProductWithInfoMapper.fromDomainModel(productWithInfo));
       return productWithInfo;
    }

    @Override
    public void remove(ProductWithInfo productWithInfo) {
        mongoRemove(new ProductWithInfoDoc(productWithInfo.getId()));
    }
}
