package indie.outsource;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import indie.outsource.factories.RandomDataFactory;
import indie.outsource.model.Client;
import indie.outsource.model.ProductWithInfo;
import indie.outsource.model.products.Product;
import indie.outsource.repositories.ClientMongoDbRepository;
import indie.outsource.repositories.ClientRepository;
import indie.outsource.repositories.ProductMongoDbRepository;
import indie.outsource.repositories.ProductRepository;
import indie.outsource.repositories.mongo.DefaultMongoConnection;
import jakarta.persistence.EntityManager;
import org.bson.Document;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

public class MongoRepositoryTest {
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
    public void testAddProduct(){
        Util.inSession(mongoClient,(mongoClient)->{
            ProductRepository repository = new ProductMongoDbRepository(mongoClient.getDatabase("KWIATEX"));
//            ClientRepository repository1 = new ClientMongoDbRepository(mongoClient.getDatabase("KWIATEX"));
//            repository1.add(RandomDataFactory.getRandomClient());
//            Client client = repository1.findAll().getFirst();
//            System.out.println(client.getId());
//            Client client2 = repository1.getById(client.getId());
//            System.out.println(client2.getId());
            repository.add(RandomDataFactory.getRandomProductWithInfo());
//            Assertions.assertEquals(repository.findAll().size(), 1);
//            ProductWithInfo product = repository.findAll().getFirst();
//            repository.remove(product);
//            Assertions.assertEquals(repository.findAll().size(), 0);
        });
        Util.inSession(mongoClient,(mongoClient)->{
            ProductRepository repository = new ProductMongoDbRepository(mongoClient.getDatabase("KWIATEX"));
//            repository.add(RandomDataFactory.getRandomProductWithInfo());
//            Assertions.assertEquals(repository.findAll().size(), 1);
            MongoDatabase database = mongoClient.getDatabase("KWIATEX");
            MongoCollection<Document> collection = database.getCollection(ProductWithInfo.class.getSimpleName(), Document.class);
            Document document =  collection.find().first();
            System.out.println(document.toJson());
            ProductWithInfo product = repository.findAll().getFirst();
            product.getProductInfo().getQuantity();
//            ProductWithInfo product = repository.findAll().getFirst();
//            repository.remove(product);
//            Assertions.assertEquals(repository.findAll().size(), 0);
        });
    }
}
