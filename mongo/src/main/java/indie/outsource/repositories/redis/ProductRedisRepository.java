package indie.outsource.repositories.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import indie.outsource.documents.ProductWithInfoDoc;
import indie.outsource.repositories.ProductMongoDbRepository;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.search.*;

import java.util.List;
import java.util.UUID;

public class ProductRedisRepository {
    private final JedisPooled pool;
    ObjectMapper objectMapper = new ObjectMapper();

    public ProductRedisRepository() {
        DefaultRedisConnection redisConnection = new DefaultRedisConnection("src/main/resources/redis.properties");
        pool = redisConnection.getPool();
        createSchemaIndex();
    }

    public ProductRedisRepository(String connectionPropertiesFile){
        DefaultRedisConnection redisConnection = new DefaultRedisConnection(connectionPropertiesFile);
        pool = redisConnection.getPool();
        createSchemaIndex();
    }

    private void createSchemaIndex(){
        Schema schema = new Schema().addNumericField("$.quantity");
        IndexDefinition rule = new IndexDefinition(IndexDefinition.Type.JSON).setPrefixes("product:");
        try{
            pool.ftDropIndex("indexQuantity");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            pool.ftCreate("indexQuantity", IndexOptions.defaultOptions().setDefinition(rule),schema);
        }
    }

    public void add(ProductWithInfoDoc product) {
        String json;
        try{
            json = objectMapper.writeValueAsString(product);
        }
        catch (Exception e) {
            throw new JsonException(e);
        }
        pool.jsonSet("product:"+product.getId().toString(),json);
        pool.expire("product:"+product.getId().toString(),84000);
    }

    public ProductWithInfoDoc findById(UUID id) {
        Object result = pool.jsonGet("product:"+id.toString());
        try{
            return parse(objectMapper.writer().writeValueAsString(result));
        }
        catch (Exception e) {
            throw new JsonException(e);
        }
    }

    public void remove(ProductWithInfoDoc product) {
        pool.jsonDel("product:"+product.getId().toString());
    }

    public List<ProductWithInfoDoc> findAll() {
        SearchResult searchResult = pool.ftSearch("indexQuantity", new Query().limit(0, 10000));
        return searchResult.getDocuments().stream().map(e -> parse((String) e.get("$"))).toList();
    }

    private ProductWithInfoDoc parse(String json) {
        try{
            return objectMapper.readValue(json, ProductWithInfoDoc.class);
        }
        catch (Exception e) {
            throw new JsonException(e);
        }
    }


}
