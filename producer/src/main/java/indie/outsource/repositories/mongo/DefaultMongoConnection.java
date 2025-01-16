package indie.outsource.repositories.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import indie.outsource.documents.ProductWithInfoDoc;
import indie.outsource.documents.products.*;
import lombok.Getter;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.List;

@Getter
public class DefaultMongoConnection implements MongoConnection {
    private String connString = "mongodb://mongo1:27017,mongo2:27018,mongo3:27019/?replicaSet=replica_set_single";
    private String dbName = "KWIATEX";

    private final MongoCredential mongoCredential = MongoCredential.createCredential(
        "ADMIN", "admin", "ADMINPASSWORD".toCharArray()
    );

    private final CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(
        PojoCodecProvider.builder()
            .automatic(true)
            .conventions(List.of(Conventions.ANNOTATION_CONVENTION,Conventions.CLASS_AND_PROPERTY_CONVENTION))
            .register(ProductDoc.class)
            .register(FlowerDoc.class)
            .register(TreeDoc.class)
            .register(GrassesSeedsDoc.class)
            .register(PlantDoc.class)
            .register(VegetableSeedsDoc.class)
            .register(SeedsDoc.class)
            .build()
    );

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    private void initDbConnection(){
        MongoClientSettings settings = MongoClientSettings.builder().
            credential(mongoCredential).
            applyConnectionString(new ConnectionString(connString)).
            uuidRepresentation(UuidRepresentation.STANDARD).
            codecRegistry(
                CodecRegistries.fromRegistries(
                    MongoClientSettings.getDefaultCodecRegistry(),
                    pojoCodecRegistry
                )
            ).build();

        mongoClient = MongoClients.create(settings);
        mongoDatabase = mongoClient.getDatabase(dbName);
    }
    private void initCollections(){
        CreateCollectionOptions createCollectionOptions = new CreateCollectionOptions()
            .validationOptions(MongoSchemaConfig.getValidationOptions());
        mongoDatabase.createCollection(ProductWithInfoDoc.class.getSimpleName(), createCollectionOptions);
    }


    public DefaultMongoConnection() {
        initDbConnection();
        initCollections();
    }

    public DefaultMongoConnection(String connString,String dbname) {
        this.connString = connString;
        this.dbName = dbname;
        initDbConnection();
        initCollections();
    }

    @Override
    public MongoDatabase getDatabase() {
        return mongoDatabase;
    }


}
