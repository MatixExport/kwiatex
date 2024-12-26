package indie.outsource;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;

import com.datastax.oss.driver.api.core.cql.SimpleStatement;
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

}
