package indie.outsource;

import com.fasterxml.jackson.databind.ObjectMapper;
import indie.outsource.documents.mappers.ShopTransactionMapper;
import indie.outsource.kafka.KafkaMessageContent;
import indie.outsource.kafka.Topics;
import indie.outsource.model.ShopTransaction;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.UUIDSerializer;

import java.io.IOException;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class KafkaTransactionProducer {
    private final KafkaProducer<UUID,String> producer;
    ObjectMapper objectMapper = new ObjectMapper();


    public KafkaTransactionProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192,kafka2:9292,kafka3:9392");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, UUIDSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//        props.put("schema.registry.url", "http://localhost:8081");
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "local");
        producer = new KafkaProducer<>(props);
        Topics.createTopic();
    }



    public boolean sendTransaction(ShopTransaction transaction) throws IOException, ExecutionException, InterruptedException {

        ProducerRecord<UUID, String> record = new ProducerRecord<>(
                Topics.TRANSACTION_TOPIC,
                UUID.randomUUID(),
                objectMapper.writeValueAsString(
                        new KafkaMessageContent("kwiatex", ShopTransactionMapper.fromDomainModel(transaction))
        ));
        Future<RecordMetadata> future = producer.send(record);
        RecordMetadata recordMetadata = future.get();
        System.out.println(recordMetadata.toString());
        return true;
    }


}
