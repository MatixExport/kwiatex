package indie.outsource.redis;

import com.mongodb.MongoWriteException;
import indie.outsource.documents.ProductWithInfoDoc;
import indie.outsource.factories.RandomDataFactory;
import indie.outsource.model.ProductWithInfo;
import indie.outsource.repositories.ProductRepository;
import indie.outsource.repositories.mongo.DefaultMongoConnection;
import indie.outsource.repositories.redis.CachedMongoDBProductRepository;
import indie.outsource.repositories.redis.ProductRedisRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CachedMongoProductRepositoryTest {
    private final DefaultMongoConnection mongoConnection = new DefaultMongoConnection();

    @BeforeEach
    @AfterEach
    public void tearDown() {
        mongoConnection.getMongoClient().getDatabase("KWIATEX").drop();
        try{
            ProductRedisRepository repository = new ProductRedisRepository();
            for (ProductWithInfoDoc product : repository.findAll()) {
                repository.remove(product);
            }
        }
        catch (Exception e){
//            e.printStackTrace();
        }

    }

    @Test
    public void testAddRemoveProduct(){
        ProductRepository repository = new CachedMongoDBProductRepository();
        repository.add(RandomDataFactory.getRandomProductWithInfo());
        Assertions.assertEquals(1,repository.findAll().size());
        repository.remove(repository.findAll().getFirst());
        Assertions.assertEquals(0,repository.findAll().size());
    }
    @Test
    public void testUpdateProduct(){
        ProductRepository repository = new CachedMongoDBProductRepository();
        ProductWithInfo product = RandomDataFactory.getRandomProductWithInfo();
        product.getProduct().setName("produkt");
        repository.add(product);
        product.getProduct().setName("produkt1");
        repository.add(product);
        Assertions.assertEquals(product.getProduct().getName(),repository.getById(product.getId()).getProduct().getName());
    }
    @Test
    public void testValidDecreaseProductQuantity(){
        ProductRepository repository = new CachedMongoDBProductRepository();
        ProductWithInfo product = RandomDataFactory.getRandomProductWithInfo();
        product.getProductInfo().setQuantity(5);
        repository.add(product);
        product = repository.findAll().getFirst();
        repository.increaseProductQuantity(product,5);
        Assertions.assertEquals(10,repository.getById(product.getId()).getProductInfo().getQuantity());
        repository.decreaseProductQuantity(product,5);
        Assertions.assertEquals(5,repository.getById(product.getId()).getProductInfo().getQuantity());
    }
    @Test
    public void testInValidDecreaseProductQuantity(){
        ProductRepository repository = new CachedMongoDBProductRepository();
        ProductWithInfo product = RandomDataFactory.getRandomProductWithInfo();
        product.getProductInfo().setQuantity(5);
        repository.add(product);
        product = repository.findAll().getFirst();
        Assertions.assertEquals(5,repository.getById(product.getId()).getProductInfo().getQuantity());
        ProductWithInfo finalProduct = product;
        Assertions.assertThrows(
                MongoWriteException.class,
                ()->{repository.decreaseProductQuantity(finalProduct,6);}
        );
    }
}
