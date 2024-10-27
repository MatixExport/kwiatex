package indie.outsource;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import indie.outsource.factories.RandomDataFactory;
import indie.outsource.model.Client;
import indie.outsource.model.ProductWithInfo;
import indie.outsource.model.Transaction;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.junit.jupiter.api.Test;

import java.util.List;

public class mongoltest {
    private ConnectionString connectionString = new ConnectionString(
            "mongodb://mongo1:27017,mongo2:27018,mongo3:27019/?replicaSet=replica_set_single"
    );
    private MongoCredential mongoCredential = MongoCredential.createCredential(
            "ADMIN", "admin", "ADMINPASSWORD".toCharArray()
    );

    private CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(
            PojoCodecProvider.builder().automatic(true).conventions(List.of(Conventions.ANNOTATION_CONVENTION)).build()
    );

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    @Test
    public void initDbConnection(){
        MongoClientSettings settings = MongoClientSettings.builder().
                credential(mongoCredential).
                applyConnectionString(connectionString).
                uuidRepresentation(UuidRepresentation.STANDARD).
                codecRegistry(
                        CodecRegistries.fromRegistries(
//                                CodecRegistries.fromProviders(new UniqueIdCodecProvider),
                                MongoClientSettings.getDefaultCodecRegistry(),
                                pojoCodecRegistry
                        )
                ).build();

        mongoClient = MongoClients.create(settings);
        mongoDatabase = mongoClient.getDatabase("KWIATEX");


        for (String dbName : mongoClient.listDatabaseNames()) {
            System.out.println("Database: " + dbName);
        }

        mongoDatabase.getCollection("client", Client.class).insertOne(RandomDataFactory.getRandomClient());
        mongoDatabase.getCollection("productWithInfo", ProductWithInfo.class).insertOne(RandomDataFactory.getRandomProductWithInfo());
        mongoDatabase.getCollection("transaction", Transaction.class).insertOne(RandomDataFactory.getRandomTransactionWithItems());
    }


}
