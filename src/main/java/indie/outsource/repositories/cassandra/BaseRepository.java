package indie.outsource.repositories.cassandra;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.internal.core.metadata.DefaultEndPoint;
import lombok.Getter;

import java.io.Closeable;
import java.net.InetSocketAddress;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createKeyspace;

@Getter
public class BaseRepository implements Closeable {

    private static final String datacenter = "dc1";
    private static final String username = "cassandra";
    private static final String password = "cassandrapassword";

    private final CqlSession session;


    public BaseRepository() {
        SimpleStatement createKeyspaceStatement = createKeyspace(CqlIdentifier.fromCql("kwiatex"))
                .ifNotExists()
                .withSimpleStrategy(2)
                .withDurableWrites(true)
                .build();

        try (CqlSession session1 = CqlSession.builder()
                .addContactEndPoint(new DefaultEndPoint(new InetSocketAddress("127.0.0.1", 9042)))
                .addContactEndPoint(new DefaultEndPoint(new InetSocketAddress("127.0.0.1", 9043)))
                .withLocalDatacenter(datacenter)
                .withAuthCredentials(username, password)
                .build()) {
            session1.execute(createKeyspaceStatement);
        }

        session = CqlSession.builder()
                .addContactEndPoint(new DefaultEndPoint(new InetSocketAddress("127.0.0.1", 9042)))
                .addContactEndPoint(new DefaultEndPoint(new InetSocketAddress("127.0.0.1", 9043)))
                .withLocalDatacenter(datacenter)
                .withAuthCredentials(username, password)
                .withKeyspace(CqlIdentifier.fromCql("kwiatex"))
                .build();
    }


    @Override
    public void close() {
        session.close();
    }

}
