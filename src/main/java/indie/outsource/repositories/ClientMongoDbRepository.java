package indie.outsource.repositories;

import com.mongodb.client.MongoDatabase;
import indie.outsource.model.Client;

public class ClientMongoDbRepository extends MongoDbRepository<Client> implements ClientRepository  {

    public ClientMongoDbRepository(MongoDatabase db) {
        super(Client.class, db);
    }
}
