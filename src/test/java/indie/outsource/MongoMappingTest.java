package indie.outsource;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import indie.outsource.documents.*;
import indie.outsource.documents.products.ProductDoc;
import indie.outsource.factories.RandomDataFactory;
import indie.outsource.model.*;
import indie.outsource.model.products.Product;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @Test
    public void clientToDocumentMappingTest() {
            MongoDatabase db = mongoClient.getDatabase("KWIATEX");
            Document clientDocument = db.getCollection(ClientDoc.class.getSimpleName()).find(new Document("_id", client.getId())).first();
            Assertions.assertNotNull(clientDocument);
            Assertions.assertEquals(clientDocument.getString("name"), client.getName());
            Assertions.assertEquals(clientDocument.getString("surname"), client.getSurname());
            Assertions.assertEquals(clientDocument.getString("address"), client.getAddress());
    }
    @Test
    public void productWithInfoToDocumentMappingTest() {
            MongoDatabase db = mongoClient.getDatabase("KWIATEX");
            Document productWithInfoDocument = db.getCollection(ProductWithInfoDoc.class.getSimpleName()).find(new Document("_id", productWithInfo.getId())).first();
            Assertions.assertNotNull(productWithInfoDocument);
            Document infoDocument = productWithInfoDocument.get("productInfo", Document.class);
            Assertions.assertNotNull(infoDocument);
            Document productDocument = productWithInfoDocument.get("product", Document.class);
            Assertions.assertNotNull(productDocument);
            Tree actualProduct = (Tree) productWithInfo.getProduct();
            Assertions.assertEquals(productDocument.getString("name"),actualProduct.getName());
            Assertions.assertEquals(productDocument.getDouble("price"),actualProduct.getPrice());
            Assertions.assertEquals(productDocument.getInteger("height"),actualProduct.getHeight());
            Assertions.assertEquals(productDocument.getInteger("growthStage"),actualProduct.getGrowthStage());
    }
    @Test
    public void transactionToDocumentMappingTest() {
            MongoDatabase db = mongoClient.getDatabase("KWIATEX");
            Document transactionDocument = db.getCollection(ShopTransactionDoc.class.getSimpleName()).find(new Document("_id", shopTransaction.getId())).first();
            Assertions.assertNotNull(transactionDocument);
            List<Document> transactionItemDocuments = transactionDocument.getList("items", Document.class);
            Document transactionItemDocument = transactionItemDocuments.getFirst();
            TransactionItem transactionItem = shopTransaction.getItems().getFirst();
            Assertions.assertNotNull(transactionItem);
            Assertions.assertEquals(transactionItemDocument.getInteger("amount"),transactionItem.getAmount());
    }
    @Test
    public void documentToClientTest(){
            MongoDatabase db = mongoClient.getDatabase("KWIATEX");
            ClientDoc clientDoc = db.getCollection(ClientDoc.class.getSimpleName(),ClientDoc.class).find(new Document("_id", client.getId())).first();
            Assertions.assertNotNull(clientDoc);
            Client client1 = clientDoc.toDomainModel();
            Assertions.assertNotNull(client1);
            Assertions.assertEquals(client1.getSurname(),client.getSurname());
            Assertions.assertEquals(client1.getName(),client.getName());
            Assertions.assertEquals(client1.getSurname(),client.getSurname());
    }
    @Test
    public void documentToProductWithInfoTest(){
            MongoDatabase db = mongoClient.getDatabase("KWIATEX");
            Bson filter = new Document(Map.of(
                    "_id",productWithInfo.getId()
            ));
            ProductWithInfoDoc productWithInfoDoc = db.getCollection(ProductWithInfoDoc.class.getSimpleName(),ProductWithInfoDoc.class)
                    .find(filter).first();
            Assertions.assertNotNull(productWithInfoDoc);
            ProductWithInfo productWithInfo1 = productWithInfoDoc.toDomainModel();
            Assertions.assertNotNull(productWithInfo1);
            Assertions.assertEquals(productWithInfo1.getProductInfo().getQuantity(),productWithInfo.getProductInfo().getQuantity());
            Assertions.assertEquals(productWithInfo1.getProduct().getName(),productWithInfo.getProduct().getName());
            Assertions.assertEquals(productWithInfo1.getProduct().getPrice(),productWithInfo.getProduct().getPrice());
            Assertions.assertEquals(productWithInfo1.getProduct().getProductInfo(),productWithInfo.getProduct().getProductInfo());
    }
    @Test
    public void documentToTransactionTest(){
            MongoDatabase db = mongoClient.getDatabase("KWIATEX");
            ShopTransactionDoc shopTransactionDoc = db.getCollection(ShopTransactionDoc.class.getSimpleName(), ShopTransactionDoc.class)
                    .find(new Document("_id", shopTransaction.getId())).first();
            Assertions.assertNotNull(shopTransactionDoc);
            ShopTransaction shopTransaction1 = shopTransactionDoc.toDomainModel();
            Assertions.assertNotNull(shopTransaction1);
            Assertions.assertEquals(shopTransaction1.getItems().getFirst().getAmount(),shopTransaction.getItems().getFirst().getAmount());
            Assertions.assertEquals(shopTransaction1.getTransactionInfo(),shopTransaction.getTransactionInfo());
    }
}
