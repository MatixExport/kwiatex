package indie.outsource;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;

import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;
import com.datastax.oss.driver.internal.core.metadata.DefaultEndPoint;
import indie.outsource.repositories.cassandra.clients.*;

import org.junit.Test;


import java.net.InetSocketAddress;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createKeyspace;


public class CassandraTest {

    private String datacenter = "dc1";
    private String username = "cassandra";
    private String password = "cassandrapassword";

    @Test
    public void initSession() {
        CqlSession session = CqlSession.builder()
                .addContactEndPoint(new DefaultEndPoint(new InetSocketAddress("127.0.0.1", 9042))) //nie umiem użyć tej metody co przyjmuje inetSocketAddress
                .addContactEndPoint(new DefaultEndPoint(new InetSocketAddress("127.0.0.1", 9043))) //nie umiem użyć tej metody co przyjmuje inetSocketAddress
                .withLocalDatacenter(datacenter)
                .withAuthCredentials(username, password)
                .build();

        CreateKeyspace createKeyspace = createKeyspace(CqlIdentifier.fromCql("kwiatex"))
                .ifNotExists()
                .withSimpleStrategy(2)
                .withDurableWrites(true);
        SimpleStatement createKeyspaceStatement = createKeyspace.build();
        session.execute(createKeyspaceStatement);
    }

    private CqlSession getSession() {
        return CqlSession.builder()
                .addContactEndPoint(new DefaultEndPoint(new InetSocketAddress("127.0.0.1", 9042))) //nie umiem użyć tej metody co przyjmuje inetSocketAddress
                .addContactEndPoint(new DefaultEndPoint(new InetSocketAddress("127.0.0.1", 9043))) //nie umiem użyć tej metody co przyjmuje inetSocketAddress
                .withLocalDatacenter(datacenter)
                .withAuthCredentials(username, password)
                .withKeyspace(CqlIdentifier.fromCql("kwiatex"))
                .build();
    }


    @Test
    public void initClientTables(){
        CqlSession session = getSession();
        SimpleStatement createClients = SchemaBuilder.createTable(CqlIdentifier.fromCql(ClientConsts.BY_NAME_TABLE_NAME))
                .ifNotExists()
                .withPartitionKey(ClientConsts.NAME, DataTypes.TEXT)
                .withColumn(ClientConsts.SURNAME, DataTypes.TEXT)
                .withColumn(ClientConsts.ADDRESS, DataTypes.TEXT)
                .withClusteringColumn(ClientConsts.ID, DataTypes.INT)
                .build();
        session.execute(createClients);

        SimpleStatement createClients2 = SchemaBuilder.createTable(CqlIdentifier.fromCql(ClientConsts.BY_ID_TABLE_NAME))
                .ifNotExists()
                .withPartitionKey(ClientConsts.ID, DataTypes.INT)
                .withColumn(ClientConsts.SURNAME, DataTypes.TEXT)
                .withColumn(ClientConsts.NAME, DataTypes.TEXT)
                .withColumn(ClientConsts.ADDRESS, DataTypes.TEXT)
                .build();
        session.execute(createClients2);
    }

    @Test
    public void addClient(){
        CqlSession session = getSession();
        ClientMapper clientMapper = new ClientMapperBuilder(session).build();

        ClientByNameDao clientDao = clientMapper.getClientByNameDao();
        ClientByIdDao clientByIdDao = clientMapper.getClientByIdDao();

        ClientByName client = new ClientByName( "siema",  1,"tak","asd");
        ClientById client2 = new ClientById(1,"siema","tak","asd");

        clientDao.create(client);
        clientByIdDao.create(client2);

    }

    @Test
    public void selectClient(){
        CqlSession session = getSession();
        ClientMapper clientMapper = new ClientMapperBuilder(session).build();
        ClientByNameDao clientByNameDao = clientMapper.getClientByNameDao();
        ClientByIdDao clientByIdDao = clientMapper.getClientByIdDao();

        System.out.println(clientByNameDao.findByName("siema").getId());
        System.out.println(clientByIdDao.findById(1).getId());
    }

    @Test
    public void deleteClient(){
        CqlSession session = getSession();
        ClientMapper clientMapper = new ClientMapperBuilder(session).build();
        ClientByNameDao clientByNameDao = clientMapper.getClientByNameDao();
        ClientByIdDao clientByIdDao = clientMapper.getClientByIdDao();

        ClientById clientById = clientByIdDao.findById(1);
        clientByIdDao.delete(clientById);

        ClientByName clientByName = clientByNameDao.findByName("siema");
        clientByNameDao.delete(clientByName);
    }

    @Test
    public void updateClient(){
        CqlSession session = getSession();
        ClientMapper clientMapper = new ClientMapperBuilder(session).build();
        ClientByNameDao clientByNameDao = clientMapper.getClientByNameDao();
        ClientByIdDao clientByIdDao = clientMapper.getClientByIdDao();

        ClientById clientById = clientByIdDao.findById(1);
        clientById.setAddress("new address");
        clientByIdDao.update(clientById);

        ClientByName clientByName = clientByNameDao.findByName("siema");
        clientByName.setAddress("new address");
        clientByNameDao.update(clientByName);

    }
}
