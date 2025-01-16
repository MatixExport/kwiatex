package indie.outsource.redis;

import indie.outsource.documents.ProductWithInfoDoc;
import indie.outsource.documents.mappers.ProductWithInfoMapper;
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
public class ReadHalfBenchmark {
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

        for ( int i = 0; i < items.size()/2; i++ ) {
            redisRepository.remove(ProductWithInfoMapper.fromDomainModel(items.get(i)));
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
    public void mongoFindEachById_halfCached(){
        for(ProductWithInfo product : items){
            assertEquals(product.getProduct().getName(),mongoRepository.getById(product.getId()).getProduct().getName());
        }
    }

    @Benchmark
    public void cachedFindEachById_halfCached(){
        for(ProductWithInfo product : items){
            assertEquals(product.getProduct().getName(),cachedRepository.getById(product.getId()).getProduct().getName());
        }
    }
}


