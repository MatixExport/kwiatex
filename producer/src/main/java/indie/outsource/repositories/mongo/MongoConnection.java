package indie.outsource.repositories.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public interface MongoConnection {
    MongoClient getMongoClient();
    MongoDatabase getDatabase();
}
