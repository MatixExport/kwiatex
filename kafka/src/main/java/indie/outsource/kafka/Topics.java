package indie.outsource.kafka;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.errors.TopicExistsException;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public final class Topics {
    public static final String TRANSACTION_TOPIC = "ShopTransactions";
    public static final String TRANSACTION_CONSUMER_GROUP_NAME = "transactionsConsumers";

    public static void createTopic() {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192,kafka2:9292,kafka3:9392");
        int partitions = 3;
        short replication = 2;
        try(AdminClient adminClient = AdminClient.create(props)) {
            NewTopic newTopic = new NewTopic(Topics.TRANSACTION_TOPIC, partitions, replication);
            CreateTopicsOptions options = new CreateTopicsOptions()
                    .timeoutMs(10000)
                    .validateOnly(false)
                    .retryOnQuotaViolation(true);
            CreateTopicsResult result = adminClient.createTopics(List.of(newTopic),options);
            KafkaFuture<Void> kafkaFutureResult = result.values().get(Topics.TRANSACTION_TOPIC);
            kafkaFutureResult.get();
        }catch (ExecutionException | InterruptedException e) {
            System.out.println("Error creating topic " + e.getMessage());
            if(e.getCause() instanceof TopicExistsException) {
//                throw new TopicExistsException("Topic already exists");
            }
        }
    }
}
