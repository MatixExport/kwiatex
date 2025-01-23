

import indie.outsource.KafkaTransactionProducer;
import indie.outsource.model.ShopTransaction;
import indie.outsource.repositories.TransactionMongoDbRepository;
import indie.outsource.repositories.TransactionRepository;
import indie.outsource.repositories.mongo.DefaultMongoConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class KafkaProducerTest {
    KafkaTransactionProducer producer;

    @BeforeEach
    public void setUp() {
        producer = new KafkaTransactionProducer();
    }

    @Test
    public void topicCreationTest() throws IOException, ExecutionException, InterruptedException, IllegalAccessException {
        ShopTransaction shopTransaction = RandomDataFactory.getRandomTransactionWithItems(2);

        DefaultMongoConnection mongoConnection = new DefaultMongoConnection("mongodb://mongo1:27017,mongo2:27018,mongo3:27019/?replicaSet=replica_set_single", "KWIATEX-BACKUP");
        TransactionRepository transactionRepository = new TransactionMongoDbRepository(mongoConnection.getDatabase());
        transactionRepository.add(shopTransaction);

        producer.sendTransaction(shopTransaction);
    }

}
