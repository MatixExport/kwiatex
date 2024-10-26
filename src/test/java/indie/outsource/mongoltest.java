package indie.outsource;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Convention;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.List;

public class mongoltest {
    private ConnectionString connectionString = new ConnectionString(
            "mongodb://mongodb1:27017,mongodb2://mongodb2:27018,mongodb3://mongodb3:27019/?replicaSet=replica_set_single"
    );
    private MongoCredential mongoCredential = MongoCredential.createCredential(
            "ADMIN", "ADMIN", "ADMINPASSWORD".toCharArray()
    );

    private CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(
            PojoCodecProvider.builder().automatic(true).conventions(List.of(Conventions.ANNOTATION_CONVENTION)).build()
    );

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    private void initDbConnection(){
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

        mongoClient = (MongoClient) MongoClients.create(settings);
        mongoDatabase = mongoClient.getDatabase("indie");
    }

}
