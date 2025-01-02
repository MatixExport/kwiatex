package indie.outsource;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.*;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.insert.Insert;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;
import com.datastax.oss.driver.internal.core.metadata.DefaultEndPoint;
import indie.outsource.model.products.Product;
import indie.outsource.model.products.Tree;
import indie.outsource.repositories.cassandra.clients.*;
import org.junit.Test;

import java.net.InetSocketAddress;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;
import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createKeyspace;

public class CassandraQueryBuilderTest {

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
    public void hello() {
        Product product = new Tree(1, "sdf", 12f, 1, 21);
        System.out.println(product.getClass().getSimpleName());
    }

    @Test
    public void insertInBatch(){
        CqlSession session = getSession();

        Insert insert1 =
            QueryBuilder
            .insertInto(ClientConsts.BY_ID_TABLE_NAME)
            .value(ClientConsts.ID, literal(1))
            .value(ClientConsts.NAME, literal("siema"))
            .value(ClientConsts.SURNAME, literal("tak"))
            .value(ClientConsts.ADDRESS, literal("asd"));
        Insert insert2 =
            QueryBuilder
            .insertInto(ClientConsts.BY_NAME_TABLE_NAME)
            .value(ClientConsts.ID, literal(1))
            .value(ClientConsts.NAME, literal("siema"))
            .value(ClientConsts.SURNAME, literal("tak"))
            .value(ClientConsts.ADDRESS, literal("asd"));

        BatchStatement batch =
            BatchStatement.newInstance(
                DefaultBatchType.LOGGED,
                insert1.build(),
                insert2.build()
            );

        session.execute(batch);

    }
    @Test
    public void insertPreparedWithObjectMapping(){
        CqlSession session = getSession();

        SimpleStatement insertInto = QueryBuilder.insertInto(ClientConsts.BY_ID_TABLE_NAME)
                .value("id", QueryBuilder.bindMarker("id"))
                .value("name", QueryBuilder.bindMarker("name"))
                .value("surname", QueryBuilder.bindMarker("surname"))
                .value("address", QueryBuilder.bindMarker("address"))
                .build();

        PreparedStatement preparedInsert = session.prepare(insertInto);
        BoundStatement boundStatement = preparedInsert.boundStatementBuilder().build();
        ClientMapper clientMapper = new ClientMapperBuilder(session).build();
        ClientByIdDao clientByIdDao = clientMapper.getClientByIdDao();

        // everything before this can be prepared before running insert method
        boundStatement = clientByIdDao.bind(new ClientById(1,"Nowy kolega","tak","asd"), boundStatement);
        session.execute(boundStatement);
    }


}
