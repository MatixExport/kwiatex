package indie.outsource.redis;

import indie.outsource.documents.ProductWithInfoDoc;
import indie.outsource.factories.RandomDataFactory;
import indie.outsource.model.ProductWithInfo;
import indie.outsource.repositories.ProductMongoDbRepository;
import indie.outsource.repositories.mongo.DefaultMongoConnection;
import indie.outsource.repositories.redis.CachedMongoDBProductRepository;
import indie.outsource.repositories.redis.ProductRedisRepository;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(value = 1, warmups = 1)
@Measurement(iterations = 1)
@Warmup(iterations = 1)
public class ReadBenchmarks {
    ProductRedisRepository redisRepository = new ProductRedisRepository();
    DefaultMongoConnection mongoConnection = new DefaultMongoConnection();
    ProductMongoDbRepository mongoRepository = new ProductMongoDbRepository(mongoConnection.getMongoDatabase());
    CachedMongoDBProductRepository cachedRepository = new CachedMongoDBProductRepository();

    List<ProductWithInfo> items = new ArrayList<>();

    @Setup(Level.Invocation)
    public void setup() {
        items.clear();
        for (int i = 0; i < 10; i++) {
            ProductWithInfo product = RandomDataFactory.getRandomProductWithInfo();
            cachedRepository.add(product);
            items.add(product);
        }
    }

    @TearDown(Level.Invocation)
    public void tearDown() {
        for (ProductWithInfoDoc product : redisRepository.findAll()) {
            redisRepository.remove(product);
        }
        mongoConnection.getDatabase().drop();
    }

    @Benchmark
    public void redisFindEachById(){
        for(ProductWithInfo product : items){
            assertEquals(product.getProduct().getName(),redisRepository.findById(product.getId()).getProduct().getName());
        }
    }

    @Benchmark
    public void mongoFindEachById(){
        for(ProductWithInfo product : items){
            assertEquals(product.getProduct().getName(),mongoRepository.getById(product.getId()).getProduct().getName());
        }
    }

    @Benchmark
    public void cachedFindEachById(){
        for(ProductWithInfo product : items){
            assertEquals(product.getProduct().getName(),cachedRepository.getById(product.getId()).getProduct().getName());
        }
    }
}

