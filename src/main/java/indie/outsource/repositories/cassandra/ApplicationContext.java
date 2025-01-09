package indie.outsource.repositories.cassandra;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.internal.core.metadata.DefaultEndPoint;
import indie.outsource.repositories.cassandra.clients.CassClientRepository;
import indie.outsource.repositories.cassandra.products.CassProductRepository;
import indie.outsource.repositories.cassandra.transactions.CassTransactionRepository;

import java.io.Closeable;
import java.net.InetSocketAddress;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createKeyspace;


public final class ApplicationContext  implements Closeable {
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
    }

    private CassClientRepository clientRepository;
    public CassClientRepository getCassClientRepository() {
        if (clientRepository == null) {
            clientRepository = new CassClientRepository(session);
        }
        return clientRepository;
    }

    private CassTransactionRepository transactionRepository;
    public CassTransactionRepository getTransactionRepository() {
        if (transactionRepository == null) {
            transactionRepository = new CassTransactionRepository(session);
        }
        return transactionRepository;
    }

    private CassProductRepository productRepository;
    public CassProductRepository getProductRepository() {
        if (productRepository == null) {
            productRepository = new CassProductRepository(session);
        }
        return productRepository;
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
