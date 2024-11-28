package indie.outsource.repositories.redis;

import com.mongodb.client.MongoClient;
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

    private ProductMongoDbRepository mongoRepository;
    private ProductRedisRepository redisRepository;

    public CachedMongoDBProductRepository(){
        mongoRepository = new ProductMongoDbRepository(new DefaultMongoConnection().getDatabase());
        try{
            redisRepository = new ProductRedisRepository();
        }
        catch (Exception e){
            redisRepository = null;
        }
    }

    public CachedMongoDBProductRepository(String mongoConnectionString){
        MongoClient mongoClient = new DefaultMongoConnection(mongoConnectionString).getMongoClient();
        mongoRepository = new ProductMongoDbRepository(mongoClient.getDatabase("KWIATEX"));
        try{
            redisRepository = new ProductRedisRepository();
        }
        catch (Exception e){
            redisRepository = null;
        }
    }

    @Override
    public void decreaseProductQuantity(ProductWithInfo product, int quantity) {
        mongoRepository.decreaseProductQuantity(product, quantity);
        ProductWithInfoDoc productDoc = ProductWithInfoMapper.fromDomainModel(mongoRepository.getById(product.getId()));
        if(redisRepository != null){
            redisRepository.add(productDoc);
        }
    }

    @Override
    public void increaseProductQuantity(ProductWithInfo product, int quantity) {
        mongoRepository.increaseProductQuantity(product, quantity);
        ProductWithInfoDoc productDoc = ProductWithInfoMapper.fromDomainModel(mongoRepository.getById(product.getId()));
        if(redisRepository != null){
            redisRepository.add(productDoc);
        }
    }

    @Override
    public List<ProductWithInfo> findAll() {
        if(redisRepository != null){
            List<ProductWithInfoDoc> redisProducts = redisRepository.findAll();
            if(!redisProducts.isEmpty()) {
                return redisProducts.stream().map(ProductWithInfoDoc::toDomainModel).toList();
            }
            List<ProductWithInfo> products = mongoRepository.findAll();
            for(ProductWithInfo product : products){
                redisRepository.add(ProductWithInfoMapper.fromDomainModel(product));
            }
            return products;
        }
        return mongoRepository.findAll();
    }

    @Override
    public ProductWithInfo getById(UUID id) {
        if(redisRepository != null) {
            ProductWithInfoDoc redisProduct = redisRepository.findById(id);
            if (redisProduct != null) {
                return redisProduct.toDomainModel();
            }
            ProductWithInfo mongoProduct = mongoRepository.getById(id);
            redisRepository.add(ProductWithInfoMapper.fromDomainModel(mongoProduct));
            return mongoProduct;
        }
        return mongoRepository.getById(id);
    }

    @Override
    public ProductWithInfo add(ProductWithInfo productWithInfo) {
        try{
            mongoRepository.add(productWithInfo);
        }
        catch (Exception e){
            return null;
        }
        if(redisRepository != null){
            redisRepository.add(ProductWithInfoMapper.fromDomainModel(productWithInfo));
        }
        return productWithInfo;
    }

    @Override
    public void remove(ProductWithInfo productWithInfo) {
        if(redisRepository != null){
            redisRepository.remove(ProductWithInfoMapper.fromDomainModel(productWithInfo));
        }
        mongoRepository.remove(productWithInfo);
    }
}
