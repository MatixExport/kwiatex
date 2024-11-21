//package indie.outsource.redis;
//
//import com.mongodb.MongoClientSettings;
//import com.mongodb.client.MongoClient;
//import indie.outsource.documents.ClientDoc;
//import indie.outsource.documents.ProductInfoDoc;
//import indie.outsource.documents.ProductWithInfoDoc;
//import indie.outsource.documents.mappers.ClientMapper;
//import indie.outsource.documents.mappers.ProductMapper;
//import indie.outsource.documents.mappers.ProductWithInfoMapper;
//import indie.outsource.documents.products.ProductDoc;
//import indie.outsource.documents.products.TreeDoc;
//import indie.outsource.factories.RandomDataFactory;
//import indie.outsource.model.Client;
//import indie.outsource.model.ProductInfo;
//import indie.outsource.model.ProductWithInfo;
//import indie.outsource.model.products.Tree;
//import indie.outsource.repositories.ProductRepository;
//import indie.outsource.repositories.mongo.DefaultMongoConnection;
//import indie.outsource.repositories.redis.CachedMongoDBProductRepository;
//import jakarta.json.bind.Jsonb;
//import jakarta.json.bind.JsonbBuilder;
//import org.bson.*;
//import org.bson.Document;
//import org.bson.codecs.Codec;
//import org.bson.codecs.DecoderContext;
//import org.bson.codecs.EncoderContext;
//import org.bson.codecs.configuration.CodecRegistries;
//import org.bson.codecs.configuration.CodecRegistry;
//import org.bson.conversions.Bson;
//import org.junit.Test;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
//import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
//import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
//import redis.clients.jedis.DefaultJedisClientConfig;
//import redis.clients.jedis.HostAndPort;
//import redis.clients.jedis.JedisClientConfig;
//import redis.clients.jedis.JedisPooled;
//import redis.clients.jedis.json.Path2;
//import redis.clients.jedis.search.*;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.List;
//import java.util.Properties;
//import java.util.UUID;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNull;
//
//public class testTest {
//
//    private static JedisPooled pool;
//    private String host ;
//    private int port ;
//
//    private DefaultMongoConnection mongoConnection = new DefaultMongoConnection();
//    private MongoClient mongoClient;
//
//    @BeforeEach
//    public void setUpClass() {
//        mongoConnection = new DefaultMongoConnection();
//        mongoClient = mongoConnection.getMongoClient();
//    }
////    @AfterEach
////    public void tearDown() {
////        mongoConnection.getMongoClient().getDatabase("KWIATEX").drop();
////    }
//
//
//    public void initConnection() {
//        Properties properties = new Properties();
//        try (FileInputStream input = new FileInputStream("src/main/resources/redis.properties")) {
//            properties.load(input);
//            host = properties.getProperty("redis.host");
//            port = Integer.parseInt(properties.getProperty("redis.port"));
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        JedisClientConfig clientConfig = DefaultJedisClientConfig.builder().build();
//        pool = new JedisPooled(new HostAndPort(host, port), clientConfig);
//    }
//
//    @Test
//    public void test() {
//        initConnection();
//        Jsonb jsonb = JsonbBuilder.create();
//
//        UUID uuid = UUID.randomUUID();
//        ClientDoc client = new ClientDoc(uuid, "name", "surname", "address");
//        System.out.println(client);
//        String json = jsonb.toJson(client);
//        System.out.println(json);
//
//        pool.set(uuid.toString(),json);
//        String getClient = pool.get(uuid.toString());
//        ClientDoc clientDoc = jsonb.fromJson(getClient,ClientDoc.class);
//        Client client1 = ClientMapper.toDomainModel(clientDoc);
//        System.out.println(client1.getClientInfo());
//
//        pool.del(uuid.toString());
//        String getClient2 = pool.get(uuid.toString());
//        assertNull(getClient2);
//    }
//
//    @Test
//    public void schemaTest() {
//        initConnection();
//        Schema schema = new Schema().addTextField("$.name",1.0);
//        IndexDefinition rule = new IndexDefinition(IndexDefinition.Type.JSON);
//        try{
//            pool.ftDropIndex("yes");
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
//        finally {
//            assertEquals("OK",
//                    pool.ftCreate("yes",
//                            IndexOptions.defaultOptions().setDefinition(rule),
//                            schema ));
//        }
//
//        Jsonb jsonb = JsonbBuilder.create();
//
//        UUID uuid = UUID.randomUUID();
//        ClientDoc client = new ClientDoc(uuid, "namee", "surname", "address");
//        String json = jsonb.toJson(client);
//        pool.jsonSet(uuid.toString(), json);
//
//        SearchResult searchResult = pool.ftSearch("yes", new Query());
//        System.out.println(searchResult);
//    }
//
//    @Test
//    public void jsonGetTest(){
//        initConnection();
//        Jsonb jsonb = JsonbBuilder.create();
//
//        UUID uuid = UUID.randomUUID();
//        ClientDoc client = new ClientDoc(uuid, "name", "surname", "address");
//        System.out.println(client);
//        String json = jsonb.toJson(client);
//        System.out.println(json);
//
//        pool.jsonSet(uuid.toString(),json);
//
//        pool.jsonSet(uuid.toString(), Path2.of("$.name"), "\"Jane\"");
//
//        Object result = pool.jsonGet(uuid.toString());
//        String jsonResult = jsonb.toJson(result);
//        System.out.println(jsonResult);
//    }
//
//    @Test
//    public void jsonBsonTest(){
//        initConnection();
//        Jsonb jsonb = JsonbBuilder.create();
//
//        UUID uuid = UUID.randomUUID();
//        ClientDoc client = new ClientDoc(uuid, "name", "surname", "address");
//        System.out.println(client);
//        String json = jsonb.toJson(client);
//        System.out.println(json);
//
//        pool.jsonSet(uuid.toString(),json);
//
//        pool.jsonSet(uuid.toString(), Path2.of("$.name"), "\"Jane\"");
//
//        Object result = pool.jsonGet(uuid.toString());
//        String jsonResult = jsonb.toJson(result);
//        BsonDocument client2 = BsonDocument.parse(jsonResult);
//        ClientDoc clientFromJson = jsonb.fromJson(jsonResult, ClientDoc.class);
//        System.out.println(clientFromJson.getSurname());
//    }
//
//
//    @Test
//    public void replaceTest(){
//        initConnection();
//        Jsonb jsonb = JsonbBuilder.create();
//
//        UUID uuid = UUID.randomUUID();
//        ClientDoc client = new ClientDoc(uuid, "name", "surname", "address");
//        ClientDoc client2 = new ClientDoc(uuid, "name2", "surname2", "address2");
//
//        String json = jsonb.toJson(client);
//        String json2 = jsonb.toJson(client2);
//
//        pool.jsonSet(uuid.toString(),json);
//        pool.jsonSet(uuid.toString(),json2);
//
//        Object result = pool.jsonGet(uuid.toString());
//        String jsonResult = jsonb.toJson(result);
//        ClientDoc clientFromJson = jsonb.fromJson(jsonResult, ClientDoc.class);
//        System.out.println(clientFromJson.getSurname());
//    }
//
//    @Test
//    public void testAddRemoveProduct() throws JsonProcessingException {
//        ProductRepository repository = new CachedMongoDBProductRepository();
//        ProductWithInfo productWithInfo = RandomDataFactory.getRandomProductWithInfo();
//        repository.add(productWithInfo);
//        System.out.println("Added");
//
//        System.out.println(repository.getById(productWithInfo.getId()).getProduct().getProductInfo());
//
////        Assertions.assertEquals(1,repository.findAll().size());
////        repository.remove(repository.findAll().getFirst());
////        Assertions.assertEquals(0,repository.findAll().size());
//    }
//
//    @Test
//    public void productWithIngoRedis(){
//        initConnection();
//        Jsonb jsonb = JsonbBuilder.create();
//
////        UUID uuid = UUID.randomUUID();
////        ProductWithInfoDoc productWithInfoDoc = new ProductWithInfoDoc(uuid);
////        productWithInfoDoc.setProduct(new TreeDoc("Tree", 50.0F, 40, 30));
////        productWithInfoDoc.setProductInfo(new ProductInfoDoc(10));
//
//
//
////        String json = jsonb.toJson(productWithInfoDoc);
////        System.out.println(json);
//
//        CodecRegistry registries =CodecRegistries.fromRegistries(
//                                        MongoClientSettings.getDefaultCodecRegistry(),
//                                        mongoConnection.getPojoCodecRegistry());
//
//        TreeDoc treeDoc = new TreeDoc("Tree", 50.0F, 40, 30);
//        BsonDocument bsonDocument = BsonDocumentWrapper.asBsonDocument(treeDoc, registries);
//
//        Document document = new Document(bsonDocument);
//        System.out.println("Converted Document: " + document.toJson());
//        // suppose write and read json to database
//        Document productDoc = jsonb.fromJson(document.toJson(), Document.class);
//        BsonDocument bsonDocument2 = productDoc.toBsonDocument(TreeDoc.class, registries);
//
//        Document doc = Document.parse(document.toJson());
//        BsonDocument treeDoc1 = doc.toBsonDocument(TreeDoc.class, registries);
////        TreeDoc
//
//        Codec<ProductDoc> treeDocCodec = registries.get(ProductDoc.class);
//        ProductDoc convertedTreeDoc =treeDocCodec.decode(bsonDocument2.asBsonReader(), DecoderContext.builder().checkedDiscriminator(true).build());
//
//        System.out.println(convertedTreeDoc.toDomainModel().getProductInfo());
////        TreeDoc convertedTreeDoc = bsonDocument2.toPojo(TreeDoc.class, pojoCodecRegistry);
//
//
//    }
//
//    @Test
//    public void jacksonTest() throws IOException {
//        TreeDoc treeDoc = new TreeDoc("Tree", 50.0F, 40, 30);
//        ProductWithInfoDoc productWithInfoDoc = new ProductWithInfoDoc();
//        productWithInfoDoc.setProductInfo(new ProductInfoDoc(10));
//        productWithInfoDoc.setProduct(treeDoc);
//        productWithInfoDoc.setId(UUID.randomUUID());
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(productWithInfoDoc);
////        json = "{\"id\":\"eb0206ad-3166-3481-87f5-aced2a3654b7\",\"product\":{\"_clazz\":\"TreeDoc\",\"id\":null,\"name\":\"PCUM\",\"price\":9499.494140625,\"growthStage\":2414,\"height\":3607},\"productInfo\":{\"quantity\":6671}}";
//        System.out.println(json);
//
//        ProductWithInfoDoc pr = objectMapper.readValue(json, ProductWithInfoDoc.class);
//        System.out.println(ProductMapper.toDomainModel(pr.getProduct()).getProductInfo());
//    }
//
//
//}
