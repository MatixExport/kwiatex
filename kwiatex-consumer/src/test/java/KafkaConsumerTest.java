import indie.outsource.KafkaTransactionConsumer;
import indie.outsource.repositories.TransactionMongoDbRepository;
import indie.outsource.repositories.TransactionRepository;
import indie.outsource.repositories.mongo.DefaultMongoConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class KafkaConsumerTest {
    KafkaTransactionConsumer consumer;
    TransactionRepository transactionRepository;
    DefaultMongoConnection mongoConnection;

    @BeforeEach
    public void setUp() {
        mongoConnection = new DefaultMongoConnection("mongodb://mongo1:27017,mongo2:27018,mongo3:27019/?replicaSet=replica_set_single", "KWIATEX");
        transactionRepository = new TransactionMongoDbRepository(mongoConnection.getDatabase());
        consumer = new KafkaTransactionConsumer(transactionRepository);
    }

    @Test
    public void topicConsumptionTest() throws IOException, ExecutionException, InterruptedException {
        consumer.consumeWithGroup();
    }

}
