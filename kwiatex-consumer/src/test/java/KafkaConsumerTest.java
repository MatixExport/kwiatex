import indie.outsource.KafkaTransactionConsumer;
import indie.outsource.repositories.TransactionMongoDbRepository;
import indie.outsource.repositories.TransactionRepository;
import indie.outsource.repositories.mongo.DefaultMongoConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KafkaConsumerTest {
    KafkaTransactionConsumer consumer;
    TransactionRepository transactionRepository;
    DefaultMongoConnection mongoConnection;

    @BeforeEach
    public void setUp() {
        mongoConnection = new DefaultMongoConnection("mongodb://mongo1:27017,mongo2:27018,mongo3:27019/?replicaSet=replica_set_single", "KWIATEX-BACKUP");
        transactionRepository = new TransactionMongoDbRepository(mongoConnection.getDatabase());
        consumer = new KafkaTransactionConsumer(transactionRepository);
    }

    @Test
    public void topicConsumptionTest() {
        consumer.consumeWithGroup();
    }

    @Test
    public void doubleConsumerTest() {
        DefaultMongoConnection mongoConnection2 = new DefaultMongoConnection("mongodb://mongo1:27017,mongo2:27018,mongo3:27019/?replicaSet=replica_set_single", "KWIATEX-BACKUP");
        TransactionRepository transactionRepository2 = new TransactionMongoDbRepository(mongoConnection2.getDatabase());
        KafkaTransactionConsumer consumer2 = new KafkaTransactionConsumer(transactionRepository2);

        try(ExecutorService executor = Executors.newFixedThreadPool(2)){
            executor.execute(consumer2::consumeWithGroup);
            executor.execute(consumer::consumeWithGroup);
        }
    }
}
