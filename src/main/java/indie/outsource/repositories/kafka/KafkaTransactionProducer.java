package indie.outsource.repositories.kafka;
import com.fasterxml.jackson.dataformat.avro.AvroMapper;
import com.fasterxml.jackson.dataformat.avro.AvroSchema;
import indie.outsource.kafkaModel.KfShopTransaction;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.errors.TopicExistsException;
import org.apache.kafka.common.serialization.UUIDSerializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class KafkaTransactionProducer {
    private final KafkaProducer<UUID,Object> producer;


    public KafkaTransactionProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192,kafka2:9292,kafka3:9392");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, UUIDSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,   io.confluent.kafka.serializers.KafkaAvroSerializer.class);
        props.put("schema.registry.url", "http://localhost:8081");
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "local");
        producer = new KafkaProducer<>(props);
        createTopic();
    }

    private void createTopic() {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192,kafka2:9292,kafka3:9392");
        int partitions = 5;
        short replication = 3;
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

    public boolean sendTransaction(KfShopTransaction transaction) throws IOException, ExecutionException, InterruptedException, IllegalAccessException {
        AvroMapper avroMapper = new AvroMapper();
        AvroSchema schema = avroMapper.schemaFor(KfShopTransaction.class);
        GenericRecord avroRecord = new GenericData.Record(schema.getAvroSchema());
//        avroRecord.put("id", transaction.getId());
//        avroRecord.put("client",
//                AvroPOJOMapper.mapToGenericRecord(
//                        schema.getAvroSchema().getField("client").schema(),
//                        transaction.getClient()
//                ));
//        avroRecord.put("items",transaction.getItems());


        GenericRecord avroData = AvroPOJOMapper.mapToGenericRecord(schema.getAvroSchema(),transaction);
        System.out.println(avroData.toString());
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        avroMapper.writer(schema).writeValue(out, transaction);
//        byte[] avroData = out.toByteArray();
        //TODO: properly handle sending
        ProducerRecord<UUID, Object> record = new ProducerRecord<>(Topics.TRANSACTION_TOPIC, transaction.getId(), avroRecord);
        Future<RecordMetadata> future = producer.send(record);
        System.out.println(future.get());
        return true;


    }


}
