package indie.outsource.repositories.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import indie.outsource.documents.ProductWithInfoDoc;
import indie.outsource.repositories.ProductRepository;
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
        try{
            String json = objectMapper.writeValueAsString(product);
            pool.jsonSet(product.getId().toString(),json);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ProductWithInfoDoc findById(UUID id) {
        Object result = pool.jsonGet(id.toString());
        try{
            return parse(objectMapper.writer().writeValueAsString(result));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void remove(ProductWithInfoDoc product) {
        pool.del(product.getId().toString());
    }

    public List<ProductWithInfoDoc> findAll() {
        SearchResult searchResult = pool.ftSearch("indexUUID", new Query());
        try {
            return searchResult.getDocuments().stream().map(e -> parse((String) e.get("$"))).toList();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private ProductWithInfoDoc parse(String json) {
        try{
            return objectMapper.readValue(json, ProductWithInfoDoc.class);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
