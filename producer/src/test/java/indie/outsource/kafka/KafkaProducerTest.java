package indie.outsource.kafka;

import indie.outsource.factories.RandomDataFactory;
import indie.outsource.repositories.kafka.KafkaTransactionProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class KafkaProducerTest {
    KafkaTransactionProducer producer;

    @BeforeEach
    public void setUp() {
//        org.apache.avro.specific.SpecificData.get().addLogicalTypeConversion(null);
        producer = new KafkaTransactionProducer();
    }

    @Test
    public void topicCreationTest() throws IOException, ExecutionException, InterruptedException, IllegalAccessException {

        producer.sendTransaction(RandomDataFactory.getRandomTransactionWithItems(2));
    }

}
