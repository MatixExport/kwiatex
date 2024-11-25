package indie.outsource.redis;

import indie.outsource.documents.ProductWithInfoDoc;
import indie.outsource.documents.mappers.ProductWithInfoMapper;
import indie.outsource.factories.RandomDataFactory;
import indie.outsource.repositories.ProductMongoDbRepository;
import indie.outsource.repositories.mongo.DefaultMongoConnection;
import indie.outsource.repositories.redis.CachedMongoDBProductRepository;
import indie.outsource.repositories.redis.ProductRedisRepository;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(value = 1, warmups = 1)
@Measurement(iterations = 1)
@Warmup(iterations = 1)
public class ReadAllBenchmarks {
    ProductRedisRepository redisRepository = new ProductRedisRepository();
    DefaultMongoConnection mongoConnection = new DefaultMongoConnection();
    ProductMongoDbRepository mongoRepository = new ProductMongoDbRepository(mongoConnection.getMongoDatabase());
    CachedMongoDBProductRepository cachedRepository = new CachedMongoDBProductRepository();

    @Setup(Level.Invocation)
    public void setup() {
        for (int i = 0; i < 1000; i++) {
            cachedRepository.add(RandomDataFactory.getRandomProductWithInfo());
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
    public void redisFindAll(){
        assertEquals(1000,redisRepository.findAll().size());
    }

    @Benchmark
    public void mongoFindAll(){
        assertEquals(1000,mongoRepository.findAll().size());
    }

    @Benchmark
    public void cachedFindAll(){
        assertEquals(1000,cachedRepository.findAll().size());
    }
}

