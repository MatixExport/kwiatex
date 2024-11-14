package indie.outsource;

import com.mongodb.client.MongoClient;
import indie.outsource.factories.RandomDataFactory;
import indie.outsource.model.ShopTransaction;
import indie.outsource.repositories.TransactionMongoDbRepository;
import indie.outsource.repositories.TransactionRepository;
import indie.outsource.repositories.mongo.DefaultMongoConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MongoShopTransactionRepositoryTest {
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
    public void testAddTransaction() {
            TransactionRepository repository = new TransactionMongoDbRepository(mongoConnection.getMongoDatabase());
            ShopTransaction shopTransaction = RandomDataFactory.getRandomTransactionWithItems();
            repository.add(shopTransaction);
            ShopTransaction addedShopTransaction = repository.getById(shopTransaction.getId());
            Assertions.assertEquals(shopTransaction.getItems().size(), addedShopTransaction.getItems().size());
            Assertions.assertEquals(
                    shopTransaction.getItems().getFirst().getProduct().getName(),
                    addedShopTransaction.getItems().getFirst().getProduct().getName());
            Assertions.assertEquals(shopTransaction.getClient().getClientInfo(), addedShopTransaction.getClient().getClientInfo());

    }
}
