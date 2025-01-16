package indie.outsource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import indie.outsource.kafkaModel.KafkaMessageContent;
import indie.outsource.repositories.TransactionRepository;
import indie.outsource.repositories.kafka.Topics;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import org.apache.kafka.common.serialization.UUIDSerializer;


import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class KafkaTransactionConsumer {

    List<KafkaConsumer<UUID,String>> consumerGroup = new ArrayList<>();
    TransactionRepository transactionRepository;
    ObjectMapper objectMapper = new ObjectMapper();

    public KafkaTransactionConsumer(TransactionRepository repository) {
        Properties props = new Properties();
        transactionRepository = repository;
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192,kafka2:9292,kafka3:9392");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUIDDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, Topics.TRANSACTION_CONSUMER_GROUP_NAME);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
//        props.put("schema.registry.url", "http://localhost:8081");

        for(int i=0;i<1;i++){
            KafkaConsumer<UUID,String> consumer = new KafkaConsumer<>(props);
            consumer.subscribe(List.of(Topics.TRANSACTION_TOPIC));
            consumerGroup.add(consumer);
        }
    }

    public void consume(KafkaConsumer<UUID,String> consumer){
        try{
            while(true){
                ConsumerRecords<UUID,String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<UUID, String> record : records) {
                    transactionRepository.add(
                            objectMapper
                                    .readValue(record.value(),KafkaMessageContent.class)
                                    .getTransaction().toDomainModel()
                    );
                }
            }
        }catch(WakeupException e){
            System.out.println("Wakeup Exception");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void consumeWithGroup() throws InterruptedException {
        try(ExecutorService executor = Executors.newFixedThreadPool(consumerGroup.size())){
            for(KafkaConsumer<UUID,String> consumer : consumerGroup){
                executor.execute(()->consume(consumer));
            }
            Thread.sleep(5000);
            for(KafkaConsumer<UUID,String> consumer : consumerGroup){
                consumer.wakeup();
            }
            executor.shutdown();
        }
    }




}
