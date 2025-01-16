package indie.outsource.redis;

import com.github.dockerjava.api.DockerClient;
import indie.outsource.factories.RandomDataFactory;
import indie.outsource.model.ProductWithInfo;
import indie.outsource.repositories.ProductRepository;
import indie.outsource.repositories.redis.CachedMongoDBProductRepository;
import org.junit.jupiter.api.*;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.DockerComposeContainer;

import java.io.File;

@Disabled
public class RedisDisableContainerTest {

    private static final DockerComposeContainer<?> compose =
            new DockerComposeContainer<>(new File("docker/test-compose.yml"))
                    .withExposedService("redis-stack_1",6379)
                    .withLocalCompose(true)
                    .withOptions("--compatibility");

    private DockerClient dockerClient;
    ProductRepository cachedRepository;

    @BeforeEach
    public void setUp() {
        compose.start();
        dockerClient = DockerClientFactory.instance().client();
        cachedRepository = new CachedMongoDBProductRepository(
                "mongodb://mongo1:2017,mongo2:2018,mongo3:2019/?replicaSet=replica_set_single",
                "src/main/resources/redisTest.properties");

//        cachedRepository = new ProductRedisRepository("src/main/resources/redisTest.properties");

        ProductWithInfo product = RandomDataFactory.getRandomProductWithInfo();
        product.getProductInfo().setQuantity(5);
        cachedRepository.add(product);
    }

    @AfterEach
    public void tearDown() {
        compose.stop();
    }

    @Test
    public void testWithRedisServiceStopped(){
        ProductWithInfo product = cachedRepository.findAll().getFirst();
        Assertions.assertEquals(5, cachedRepository.getById(product.getId()).getProductInfo().getQuantity());

        String containerId = compose.getContainerByServiceName("redis-stack_1")
                .orElseThrow(() -> new IllegalStateException("Container mongo1_1 not found"))
                .getContainerId();
        dockerClient.stopContainerCmd(containerId).exec();


        Assertions.assertEquals(5, cachedRepository.getById(product.getId()).getProductInfo().getQuantity());
    }
}
