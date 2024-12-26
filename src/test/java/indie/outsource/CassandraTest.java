package indie.outsource;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;

import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataType;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;
import com.datastax.oss.driver.internal.core.metadata.DefaultEndPoint;
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
    public void clientTable(){
        CqlSession session = getSession();
        SimpleStatement createClients = SchemaBuilder.createTable(CqlIdentifier.fromCql("clients"))
                .ifNotExists()
                .withPartitionKey(CqlIdentifier.fromCql("id"), DataTypes.INT)
//                .withClusteringColumn()
                .withColumn(CqlIdentifier.fromCql("name"), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql("surname"), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql("address"), DataTypes.TEXT)
//                .withClusteringOrder()
                .build();
        session.execute(createClients);

    }
}
