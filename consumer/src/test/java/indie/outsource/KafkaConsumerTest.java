package indie.outsource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class KafkaConsumerTest {
    KafkaTransactionConsumer consumer;

    @BeforeEach
    public void setUp() {
        consumer = new KafkaTransactionConsumer();
    }

    @Test
    public void topicConsumptionTest() throws IOException, ExecutionException, InterruptedException {
        consumer.consumeWithGroup();
    }

}
