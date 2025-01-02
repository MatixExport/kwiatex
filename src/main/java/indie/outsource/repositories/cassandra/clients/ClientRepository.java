package indie.outsource.repositories.cassandra.clients;

import com.datastax.oss.driver.api.core.cql.*;
import indie.outsource.model.Client;
import indie.outsource.repositories.cassandra.BaseRepository;

public class ClientRepository extends BaseRepository {

    private final ClientDao clientDao;

    private final BoundStatement insertIntoClientsById;
    private final BoundStatement insertIntoClientsByName;
    private final BoundStatement deleteFromClientsById;
    private final BoundStatement deleteFromClientsByName;
    private final BoundStatement updateClientsById;

    public ClientRepository() {
        super();

        CreateClientTables();

        ClientMapper clientMapper = new ClientMapperBuilder(getSession()).build();
        clientDao = clientMapper.getClientDao();

        insertIntoClientsById = ClientStatementFactory.prepareInsert(ClientConsts.BY_ID_TABLE_NAME, getSession());
        insertIntoClientsByName = ClientStatementFactory.prepareInsert(ClientConsts.BY_NAME_TABLE_NAME, getSession());
        deleteFromClientsById = ClientStatementFactory.prepareDeleteFromClientsById(getSession());
        deleteFromClientsByName = ClientStatementFactory.prepareDeleteFromClientsByName(getSession());
        updateClientsById = ClientStatementFactory.prepareUpdateClientsById(getSession());
    }

    private void CreateClientTables() {
        getSession().execute(ClientStatementFactory.createClientsByNameTable);
        getSession().execute(ClientStatementFactory.createClientsByIdTable);
    }


    public Client findById(int id) {
        return clientDao.findById(id);
    }

    public Client findByName(String name) {
        return clientDao.findByName(name);
    }

    public void save(Client client) {
        BatchStatement batch =
                BatchStatement.newInstance(
                        DefaultBatchType.LOGGED,
                        clientDao.bind(client, insertIntoClientsById).setLong("timestamp", System.currentTimeMillis()),
                        clientDao.bind(client, insertIntoClientsByName).setLong("timestamp", System.currentTimeMillis())
                );
        getSession().execute(batch);
    }

    public void delete(Client client) {
        BatchStatement batch =
                BatchStatement.newInstance(
                        DefaultBatchType.LOGGED,
                        deleteFromClientsById
                                .setInt(ClientConsts.ID, client.getId())
                                .setLong("timestamp", System.currentTimeMillis()),
                        deleteFromClientsByName
                                .setString(ClientConsts.NAME, client.getName())
                                .setInt(ClientConsts.ID, client.getId())
                                .setLong("timestamp", System.currentTimeMillis())
                );
        getSession().execute(batch);
    }

    public void update(Client client) {
        String name = clientDao.findById(client.getId()).getName();
        BatchStatement batch =
                BatchStatement.newInstance(
                        DefaultBatchType.LOGGED,
                        clientDao.bind(client, updateClientsById.setLong("timestamp", System.currentTimeMillis())),
                        deleteFromClientsByName
                                .setString(ClientConsts.NAME, name)
                                .setInt(ClientConsts.ID, client.getId())
                                .setLong("timestamp", System.currentTimeMillis()),
                        clientDao.bind(client, insertIntoClientsByName).setLong("timestamp", System.currentTimeMillis())
                );
        getSession().execute(batch);
    }
}
