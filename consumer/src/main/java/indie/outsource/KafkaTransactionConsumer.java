package indie.outsource;

import indie.outsource.repositories.kafka.Topics;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
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

    List<KafkaConsumer<UUID,GenericRecord>> consumerGroup = new ArrayList<>();

    public KafkaTransactionConsumer() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192,kafka2:9292,kafka3:9392");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUIDDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, Topics.TRANSACTION_CONSUMER_GROUP_NAME);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put("schema.registry.url", "http://localhost:8081");

        for(int i=0;i<1;i++){
            KafkaConsumer<UUID,GenericRecord> consumer = new KafkaConsumer<>(props);
            consumer.subscribe(List.of(Topics.TRANSACTION_TOPIC));
            consumerGroup.add(consumer);
        }
    }

    public void consume(KafkaConsumer<UUID,GenericRecord> consumer){
        try{
            while(true){
                ConsumerRecords<UUID,GenericRecord> records = consumer.poll(Duration.ofMillis(100));
                if(!records.isEmpty()){
                    System.out.println("Uwaga2");
                }
                for (ConsumerRecord<UUID, GenericRecord> record : records) {
                    System.out.println("Uwaga3");
                    System.out.printf("offset = %d, key = %s, value = %s \n", record.offset(), record.key(), record.value());
                }
            }
        }catch(WakeupException e){
            System.out.println("Wakeup Exception");
        }
    }

    public void consumeWithGroup() throws InterruptedException {
        try(ExecutorService executor = Executors.newFixedThreadPool(consumerGroup.size())){
            for(KafkaConsumer<UUID,GenericRecord> consumer : consumerGroup){
                executor.execute(()->consume(consumer));
            }
            Thread.sleep(200000);
            for(KafkaConsumer<UUID,GenericRecord> consumer : consumerGroup){
                consumer.wakeup();
            }
            executor.shutdown();
        }
    }




}
