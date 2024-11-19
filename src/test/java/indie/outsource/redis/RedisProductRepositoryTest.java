package indie.outsource.redis;

import indie.outsource.documents.mappers.ProductWithInfoMapper;
import indie.outsource.factories.RandomDataFactory;
import indie.outsource.model.ProductWithInfo;
import indie.outsource.repositories.redis.ProductRedisRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RedisProductRepositoryTest {

    @Test
    public void testAddRemoveProduct(){
        ProductRedisRepository repository = new ProductRedisRepository();
        repository.add(ProductWithInfoMapper.fromDomainModel(RandomDataFactory.getRandomProductWithInfo()));
        Assertions.assertEquals(1,repository.findAll().size());
        repository.remove(repository.findAll().getFirst());
        Assertions.assertEquals(0,repository.findAll().size());
    }
    @Test
    public void testUpdateProduct(){
        ProductRedisRepository repository = new ProductRedisRepository();
        ProductWithInfo product = RandomDataFactory.getRandomProductWithInfo();
        product.getProduct().setName("produkt");
        repository.add(ProductWithInfoMapper.fromDomainModel(product));
        product.getProduct().setName("produkt1");
        repository.add(ProductWithInfoMapper.fromDomainModel(product));
        Assertions.assertEquals(product.getProduct().getName(),repository.findById(product.getId()).getProduct().getName());
    }
}
