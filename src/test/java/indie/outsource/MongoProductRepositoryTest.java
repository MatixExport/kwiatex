package indie.outsource;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoClient;
import indie.outsource.factories.RandomDataFactory;
import indie.outsource.model.ProductWithInfo;
import indie.outsource.repositories.ProductMongoDbRepository;
import indie.outsource.repositories.ProductRepository;
import indie.outsource.repositories.mongo.DefaultMongoConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MongoProductRepositoryTest {
    private DefaultMongoConnection mongoConnection;
    private MongoClient mongoClient;

    @BeforeEach
    public void setUpClass() {
      mongoConnection = new DefaultMongoConnection();
      mongoClient = mongoConnection.getMongoClient();
    }

    @AfterEach
    public void tearDown() {
        mongoConnection.getMongoClient().getDatabase("KWIATEX").drop();
    }



    @Test
    public void testAddRemoveProduct(){
        ProductRepository repository = new ProductMongoDbRepository(mongoClient.getDatabase("KWIATEX"));
        repository.add(RandomDataFactory.getRandomProductWithInfo());
        Assertions.assertEquals(1,repository.findAll().size());
        repository.remove(repository.findAll().getFirst());
        Assertions.assertEquals(0,repository.findAll().size());
    }
    @Test
    public void testUpdateProduct(){
        ProductRepository repository = new ProductMongoDbRepository(mongoClient.getDatabase("KWIATEX"));
        ProductWithInfo product = RandomDataFactory.getRandomProductWithInfo();
        product.getProduct().setName("produkt");
        repository.add(product);
        product.getProduct().setName("produkt1");
        repository.add(product);
        Assertions.assertEquals(product.getProduct().getName(),repository.getById(product.getId()).getProduct().getName());
    }
    @Test
    public void testValidDecreaseProductQuantity(){
        ProductRepository repository = new ProductMongoDbRepository(mongoClient.getDatabase("KWIATEX"));
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
        ProductRepository repository = new ProductMongoDbRepository(mongoClient.getDatabase("KWIATEX"));
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
