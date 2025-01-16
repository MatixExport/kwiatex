package indie.outsource.repositories.kafka;
import ch.qos.logback.core.net.server.Client;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.avro.AvroMapper;
import com.fasterxml.jackson.dataformat.avro.AvroSchema;
import indie.outsource.documents.ShopTransactionDoc;
import indie.outsource.documents.mappers.ShopTransactionMapper;
import indie.outsource.kafkaModel.KafkaMessageContent;
import indie.outsource.kafkaModel.KfShopTransaction;
import indie.outsource.kafkaModel.KfTransactionItem;
import indie.outsource.model.ShopTransaction;
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
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.UUIDSerializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
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
        createTopic();
    }

    private void createTopic() {
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

    public boolean sendTransaction(ShopTransaction transaction) throws IOException, ExecutionException, InterruptedException, IllegalAccessException {
//        AvroMapper avroMapper = new AvroMapper();
//        AvroSchema schema = avroMapper.schemaFor(KfTransactionItem.class);
//        System.out.println(schema.getAvroSchema().toString());
//        GenericRecord avroRecord = new GenericData.Record(schema.getAvroSchema());
//        System.out.println(schema.getAvroSchema().toString(true));
//        avroRecord.put("name", transaction.getClient().getName());
//        avroRecord.put("id", transaction.getClient().getId());
//        avroRecord.put("surname", transaction.getClient().getSurname());
//        avroRecord.put("address", transaction.getClient().getAddress());
//        avroRecord.put("id", transaction.getId());
//        avroRecord.put("client",
//                AvroPOJOMapper.mapToGenericRecord(
//                        schema.getAvroSchema().getField("client").schema(),
//                        transaction.getClient()
//                ));
//        List<Object> avroArray = new ArrayList<>();
//        for (KfTransactionItem item : transaction.getItems()) {
//            avroArray.add(AvroPOJOMapper.mapToGenericRecord(avroMapper.schemaFor(KfTransactionItem.class).getAvroSchema(), item));
//        }
//        avroRecord.put("items", avroArray);
//        avroRecord.put("items",AvroPOJOMapper.mapToGenericRecord(
//                schema.getAvroSchema().getField("items").schema(),
//                transaction.getItems()
//        ));


//        GenericRecord avroData = AvroPOJOMapper.mapToGenericRecord(schema.getAvroSchema(),transaction);
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        avroMapper.writer(schema).writeValue(out, transaction);
//        byte[] avroData = out.toByteArray();
        //TODO: properly handle sending
//        System.out.println("AvroData");
//        System.out.println(avroRecord.toString());


        ProducerRecord<UUID, String> record = new ProducerRecord<>(Topics.TRANSACTION_TOPIC, UUID.randomUUID(),objectMapper.writeValueAsString(
                new KafkaMessageContent("kwiatex", ShopTransactionMapper.fromDomainModel(transaction))
        ));
        Future<RecordMetadata> future = producer.send(record);
        RecordMetadata recordMetadata = future.get();
        System.out.println(recordMetadata.toString());
        return true;


    }


}
