package indie.outsource.repositories.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import indie.outsource.documents.ProductWithInfoDoc;
import indie.outsource.repositories.ProductRepository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.search.*;

import java.util.List;
import java.util.UUID;

public class ProductRedisRepository {
    private final DefaultRedisConnection redisConnection = new DefaultRedisConnection();
    private JedisPooled pool = redisConnection.getPool();
    ObjectMapper objectMapper = new ObjectMapper();

    public ProductRedisRepository() {
        Schema schema = new Schema().addNumericField("$.quantity");
        IndexDefinition rule = new IndexDefinition(IndexDefinition.Type.JSON);
        try{
            pool.ftDropIndex("indexUUID");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            pool.ftCreate("indexUUID", IndexOptions.defaultOptions().setDefinition(rule),schema);
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
        pool.jsonSet(product.getId().toString(),json);
    }

    public ProductWithInfoDoc findById(UUID id) {
        Object result = pool.jsonGet(id.toString());
        try{
            return parse(objectMapper.writer().writeValueAsString(result));
        }
        catch (Exception e) {
            throw new JsonException(e);
        }
    }

    public void remove(ProductWithInfoDoc product) {
        pool.del(product.getId().toString());
    }

    public List<ProductWithInfoDoc> findAll() {
        SearchResult searchResult = pool.ftSearch("indexUUID", new Query().limit(0, 10000));
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
