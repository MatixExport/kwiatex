package indie.outsource.repositories.cassandra;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.internal.core.metadata.DefaultEndPoint;
import indie.outsource.repositories.cassandra.clients.ClientRepository;
import indie.outsource.repositories.cassandra.products.CassProductRepository;
import indie.outsource.repositories.cassandra.transactions.CassTransactionRepository;
import lombok.Getter;

import java.io.Closeable;
import java.net.InetSocketAddress;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createKeyspace;


public final class ApplicationContext  implements Closeable {
    @Getter
    private final ClientRepository clientRepository;
    @Getter
    private final CassTransactionRepository transactionRepository;
    @Getter
    private final CassProductRepository productRepository;


    private static final String datacenter = "dc1";
    private static final String username = "cassandra";
    private static final String password = "cassandrapassword";

    private final CqlSession session;

    private ApplicationContext() {
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


        this.clientRepository = new ClientRepository(session);
        this.transactionRepository = new CassTransactionRepository(session);
        this.productRepository = new CassProductRepository(session);
    }


    private static ApplicationContext instance;
    public static ApplicationContext getInstance(){
        if(instance == null){
            instance = new ApplicationContext();
        }
        return instance;
    }

    @Override
    public void close() {
        this.session.close();
        instance = null;
    }
}
