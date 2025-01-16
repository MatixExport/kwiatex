package indie.outsource.repositories;

import com.mongodb.client.MongoDatabase;
import indie.outsource.documents.ClientDoc;
import indie.outsource.documents.mappers.ClientMapper;
import indie.outsource.model.Client;

import java.util.List;
import java.util.UUID;

public class ClientMongoDbRepository extends MongoDbRepository<ClientDoc> implements ClientRepository  {

    public ClientMongoDbRepository(MongoDatabase db) {
        super(ClientDoc.class, db);
    }


    @Override
    public List<Client> findAll() {
        return mongoFindAll().stream()
                .map(ClientMapper::toDomainModel)
                .toList();
    }

    @Override
    public Client getById(UUID id) {
        return mongoGetById(id).toDomainModel();
    }

    @Override
    public Client add(Client client) {
        mongoAdd(ClientMapper.fromDomainModel(client));
        return client;
    }

    @Override
    public void remove(Client client) {
        mongoRemove(new ClientDoc(
                client.getId()
        ));
    }
}
