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

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(value = 1, warmups = 1)
@Measurement(iterations = 1)
@Warmup(iterations = 1)
public class AddRemoveBenchmarks {

    ProductRedisRepository redisRepository = new ProductRedisRepository();
    DefaultMongoConnection mongoConnection = new DefaultMongoConnection();
    ProductMongoDbRepository mongoRepository = new ProductMongoDbRepository(mongoConnection.getMongoDatabase());
    CachedMongoDBProductRepository cachedRepository = new CachedMongoDBProductRepository();

    @TearDown(Level.Invocation)
    public void tearDown() {
        for (ProductWithInfoDoc product : redisRepository.findAll()) {
            redisRepository.remove(product);
        }
        mongoConnection.getDatabase().drop();
    }

    @Benchmark
    public void redisAddRemove(){
        redisRepository.add(ProductWithInfoMapper.fromDomainModel(RandomDataFactory.getRandomProductWithInfo()));
        redisRepository.remove(redisRepository.findAll().getFirst());
    }

    @Benchmark
    public void mongoAddRemove(){
        mongoRepository.add(RandomDataFactory.getRandomProductWithInfo());
        mongoRepository.remove(mongoRepository.findAll().getFirst());
    }

    @Benchmark
    public void cachedAddRemove(){
        cachedRepository.add(RandomDataFactory.getRandomProductWithInfo());
        cachedRepository.remove(cachedRepository.findAll().getFirst());
    }
}
