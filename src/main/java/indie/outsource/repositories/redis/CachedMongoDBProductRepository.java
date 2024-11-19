package indie.outsource.repositories.redis;

import com.mongodb.client.MongoDatabase;
import indie.outsource.documents.ProductWithInfoDoc;
import indie.outsource.documents.mappers.ProductWithInfoMapper;
import indie.outsource.model.ProductWithInfo;
import indie.outsource.repositories.ProductMongoDbRepository;
import indie.outsource.repositories.ProductRepository;
import indie.outsource.repositories.mongo.DefaultMongoConnection;


import java.util.List;
import java.util.UUID;

public class CachedMongoDBProductRepository implements ProductRepository {

    private final MongoDatabase db = new DefaultMongoConnection().getDatabase();
    private final ProductRepository mongoRepository = new ProductMongoDbRepository(db);
    private final ProductRedisRepository redisRepository = new ProductRedisRepository();

    @Override
    public void decreaseProductQuantity(ProductWithInfo product, int quantity) {
        mongoRepository.decreaseProductQuantity(product, quantity);
        ProductWithInfoDoc productDoc = ProductWithInfoMapper.fromDomainModel(mongoRepository.getById(product.getId()));
        redisRepository.add(productDoc);
    }

    @Override
    public void increaseProductQuantity(ProductWithInfo product, int quantity) {
        mongoRepository.increaseProductQuantity(product, quantity);
        ProductWithInfoDoc productDoc = ProductWithInfoMapper.fromDomainModel(mongoRepository.getById(product.getId()));
        redisRepository.add(productDoc);
    }

    @Override
    public List<ProductWithInfo> findAll() {
        List<ProductWithInfoDoc> redisProducts = redisRepository.findAll();
        if(redisProducts == null){
            return mongoRepository.findAll();
        }
        return redisProducts.stream().map(ProductWithInfoDoc::toDomainModel).toList();
    }

    @Override
    public ProductWithInfo getById(UUID id) {
        ProductWithInfoDoc redisProduct = redisRepository.findById(id);
        if (redisProduct == null) {
            ProductWithInfo mongoProduct = mongoRepository.getById(id);
            redisRepository.add(ProductWithInfoMapper.fromDomainModel(mongoProduct));
            return mongoProduct;
        }
        return redisProduct.toDomainModel();
    }

    @Override
    public ProductWithInfo add(ProductWithInfo productWithInfo) {
        mongoRepository.add(productWithInfo);
        ProductWithInfo product = mongoRepository.getById(productWithInfo.getId());
        redisRepository.add(ProductWithInfoMapper.fromDomainModel(product));
        return product;
    }

    @Override
    public void remove(ProductWithInfo productWithInfo) {
        redisRepository.remove(ProductWithInfoMapper.fromDomainModel(productWithInfo));
        mongoRepository.remove(productWithInfo);
    }
}
