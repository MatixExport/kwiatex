package indie.outsource.repositories.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import indie.outsource.model.ProductWithInfo;
import indie.outsource.model.products.*;
import lombok.Getter;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.List;

@Getter
public class DefaultMongoConnection implements MongoConnection {
    private final ConnectionString connectionString = new ConnectionString(
            "mongodb://mongo1:27017,mongo2:27018,mongo3:27019/?replicaSet=replica_set_single"
    );
    private final MongoCredential mongoCredential = MongoCredential.createCredential(
            "ADMIN", "admin", "ADMINPASSWORD".toCharArray()
    );

    private final CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(
            PojoCodecProvider.builder()
                    .automatic(true)
                    .conventions(List.of(Conventions.ANNOTATION_CONVENTION,Conventions.CLASS_AND_PROPERTY_CONVENTION))
                    .register(Product.class)
                    .register(Flower.class)
                    .register(Tree.class)
                    .register(GrassesSeeds.class)
                    .register(Plant.class)
                    .register(VegetableSeeds.class)
                    .register(Seeds.class)
                    .build()
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
                                MongoClientSettings.getDefaultCodecRegistry(),
                                pojoCodecRegistry
                        )
                ).build();

        mongoClient = MongoClients.create(settings);
        mongoDatabase = mongoClient.getDatabase("KWIATEX");
    }
    private void initCollections(){
        CreateCollectionOptions createCollectionOptions = new CreateCollectionOptions()
                .validationOptions(MongoSchemaConfig.getValidationOptions());
        mongoDatabase.createCollection(ProductWithInfo.class.getSimpleName(), createCollectionOptions);
    }


    public DefaultMongoConnection() {
        initDbConnection();
        initCollections();
    }

    @Override
    public MongoDatabase getDatabase() {
        return mongoDatabase;
    }


}
