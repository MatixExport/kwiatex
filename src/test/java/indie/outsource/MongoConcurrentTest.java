package indie.outsource;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import indie.outsource.factories.RandomDataFactory;
import indie.outsource.model.ProductWithInfo;
import indie.outsource.repositories.ProductMongoDbRepository;
import indie.outsource.repositories.mongo.DefaultMongoConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MongoConcurrentTest {
    private DefaultMongoConnection mongoConnection1;
    private MongoClient mongoClient1,mongoClient2;
    private MongoDatabase mongoDatabase1, mongoDatabase2;

    @BeforeEach
    public void setUpClass() {
        mongoConnection1 = new DefaultMongoConnection();
        DefaultMongoConnection mongoConnection2 = new DefaultMongoConnection();
        mongoClient1 = mongoConnection1.getMongoClient();
        mongoClient2 = mongoConnection2.getMongoClient();
        mongoDatabase1 = mongoConnection1.getDatabase();
        mongoDatabase2 = mongoConnection2.getDatabase();
    }

    @AfterEach
    public void tearDown() {
        mongoConnection1.getMongoClient().getDatabase("KWIATEX").drop();
    }


    @Test
    public void concurrentProductQuantityIncreaseTest(){
            ProductMongoDbRepository repository1 = new ProductMongoDbRepository(mongoDatabase1);
            ProductWithInfo product = RandomDataFactory.getRandomProductWithInfo();
            product.getProductInfo().setQuantity(0);
            repository1.add(product);

            Util.inSession(mongoClient2,(_)->{
                ProductMongoDbRepository repository2 = new ProductMongoDbRepository(mongoDatabase2);
                repository2.increaseProductQuantity(repository2.findAll().getFirst(),4);
            });
            repository1.increaseProductQuantity(repository1.findAll().getFirst(),7);

        ProductMongoDbRepository repository = new ProductMongoDbRepository(mongoDatabase1);
        Assertions.assertEquals(11,repository.findAll().getFirst().getProductInfo().getQuantity());
    }

}
