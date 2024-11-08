package indie.outsource;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import indie.outsource.factories.RandomDataFactory;
import indie.outsource.model.Client;
import indie.outsource.model.ProductWithInfo;
import indie.outsource.model.ShopTransaction;
import indie.outsource.model.TransactionItem;
import indie.outsource.model.products.Tree;
import indie.outsource.repositories.*;
import indie.outsource.repositories.mongo.DefaultMongoConnection;
import indie.outsource.repositories.mongo.MongoConnection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class MongoMappingTest {
    private MongoClient mongoClient;
    private Client client;
    private ProductWithInfo productWithInfo;
    private ShopTransaction shopTransaction;

    @BeforeEach
    public void setUpClass() {
        MongoConnection mongoConnection = new DefaultMongoConnection();
        mongoClient = mongoConnection.getMongoClient();
        populateDb();
    }

    @AfterEach
    public void tearDown() {
        mongoClient.getDatabase("KWIATEX").drop();
    }

    public void populateDb() {
        Util.inSession(mongoClient,mongoClient1 -> {
            MongoDatabase db = mongoClient.getDatabase("KWIATEX");
            ClientRepository clientRepository = new ClientMongoDbRepository(db);
            ProductRepository productRepository = new ProductMongoDbRepository(db);
            TransactionRepository transactionRepository = new TransactionMongoDbRepository(db);
            client = RandomDataFactory.getRandomClient();
            productWithInfo = RandomDataFactory.getRandomProductWithInfo();
            shopTransaction = RandomDataFactory.getRandomTransactionWithItems(1);

            clientRepository.add(client);
            productRepository.add(productWithInfo);
            transactionRepository.add(shopTransaction);
        }
        );

    }

    @Test
    public void clientToDocumentMappingTest() {
        Util.inSession(mongoClient,(mongoClient ->
        {
            MongoDatabase db = mongoClient.getDatabase("KWIATEX");
            Document clientDocument = db.getCollection(Client.class.getSimpleName()).find(new Document("_id", client.getId())).first();
            Assertions.assertNotNull(clientDocument);
            Assertions.assertEquals(clientDocument.getString("name"), client.getName());
            Assertions.assertEquals(clientDocument.getString("surname"), client.getSurname());
            Assertions.assertEquals(clientDocument.getString("address"), client.getAddress());
        }));
    }
    @Test
    public void productWithInfoToDocumentMappingTest() {
        Util.inSession(mongoClient,(mongoClient ->
        {
            MongoDatabase db = mongoClient.getDatabase("KWIATEX");
            Document productWithInfoDocument = db.getCollection(ProductWithInfo.class.getSimpleName()).find(new Document("_id", productWithInfo.getId())).first();
            Assertions.assertNotNull(productWithInfoDocument);
            Document infoDocument = productWithInfoDocument.get("productInfo", Document.class);
            Assertions.assertNotNull(infoDocument);
            Document productDocument = (Document) productWithInfoDocument.get("product", Document.class);
            Assertions.assertNotNull(productDocument);
            Tree actualProduct = (Tree) productWithInfo.getProduct();
            Assertions.assertEquals(productDocument.getString("name"),actualProduct.getName());
            Assertions.assertEquals(productDocument.getDouble("price"),actualProduct.getPrice());
            Assertions.assertEquals(productDocument.getInteger("height"),actualProduct.getHeight());
            Assertions.assertEquals(productDocument.getInteger("growthStage"),actualProduct.getGrowthStage());
        }));
    }
    @Test
    public void transactionToDocumentMappingTest() {
        Util.inSession(mongoClient,(mongoClient ->
        {
            MongoDatabase db = mongoClient.getDatabase("KWIATEX");
            Document transactionDocument = db.getCollection(ShopTransaction.class.getSimpleName()).find(new Document("_id", shopTransaction.getId())).first();
            Assertions.assertNotNull(transactionDocument);
            List<Document> transactionItemDocuments = (List<Document>) transactionDocument.get("items");
            Document transactionItemDocument = transactionItemDocuments.getFirst();
            TransactionItem transactionItem = shopTransaction.getItems().getFirst();
            Assertions.assertNotNull(transactionItem);
            Assertions.assertEquals(transactionItemDocument.getInteger("amount"),transactionItem.getAmount());

        }));
    }
    @Test
    public void documentToClientTest(){
        Util.inSession(mongoClient,(mongoClient ->{
            MongoDatabase db = mongoClient.getDatabase("KWIATEX");
            Client client1 = db.getCollection(Client.class.getSimpleName(),Client.class).find(new Document("_id", client.getId())).first();
            Assertions.assertNotNull(client1);
            Assertions.assertEquals(client1.getSurname(),client.getSurname());
            Assertions.assertEquals(client1.getName(),client.getName());
            Assertions.assertEquals(client1.getSurname(),client.getSurname());
        }));
    }
    @Test
    public void documentToProductWithInfoTest(){
        Util.inSession(mongoClient,(mongoClient ->{
            MongoDatabase db = mongoClient.getDatabase("KWIATEX");
            Bson filter = new Document(Map.of(
                    "_id",productWithInfo.getId()
            ));
            ProductWithInfo productWithInfo1 = db.getCollection(ProductWithInfo.class.getSimpleName(),ProductWithInfo.class)
                    .find(filter).first();
            Assertions.assertNotNull(productWithInfo1);
            Assertions.assertEquals(productWithInfo1.getProductInfo().getQuantity(),productWithInfo.getProductInfo().getQuantity());
            Assertions.assertEquals(productWithInfo1.getProduct().getName(),productWithInfo.getProduct().getName());
            Assertions.assertEquals(productWithInfo1.getProduct().getPrice(),productWithInfo.getProduct().getPrice());
            System.out.println(productWithInfo1.getProduct().getProductInfo());
            Assertions.assertEquals(productWithInfo1.getProduct().getProductInfo(),productWithInfo.getProduct().getProductInfo());

        }));
    }
    @Test
    public void documentToTransactionTest(){
        Util.inSession(mongoClient,(mongoClient ->{
            MongoDatabase db = mongoClient.getDatabase("KWIATEX");
            ShopTransaction shopTransaction1 = db.getCollection(ShopTransaction.class.getSimpleName(),ShopTransaction.class)
                .find(new Document("_id", shopTransaction.getId())).first();
            Assertions.assertNotNull(shopTransaction1);
            Assertions.assertEquals(shopTransaction1.getItems().getFirst().getAmount(),shopTransaction.getItems().getFirst().getAmount());
            Assertions.assertEquals(shopTransaction1.getTransactionInfo(),shopTransaction.getTransactionInfo());
        }));
    }
}
