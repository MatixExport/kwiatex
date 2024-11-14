package indie.outsource;

import com.github.dockerjava.api.DockerClient;
import com.mongodb.client.MongoClient;
import indie.outsource.factories.RandomDataFactory;
import indie.outsource.model.ProductWithInfo;
import indie.outsource.repositories.ProductMongoDbRepository;
import indie.outsource.repositories.ProductRepository;
import indie.outsource.repositories.mongo.DefaultMongoConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.DockerComposeContainer;

import java.io.File;

public class MongoDisableContainerTest {

    private static final DockerComposeContainer<?> compose =
            new DockerComposeContainer<>(new File("docker/test-compose.yml"))
                    .withExposedService("mongo1_1", 2017)
                    .withExposedService("mongo2_1", 2018)
                    .withExposedService("mongo3_1", 2019)
                    .withLocalCompose(true)
                    .withOptions("--compatibility");

    private DockerClient dockerClient;
    MongoClient mongoClient;
    @BeforeEach
    public void setUp() {
        compose.start();
        dockerClient = DockerClientFactory.instance().client();
        DefaultMongoConnection mongoConnection = new DefaultMongoConnection(
                "mongodb://mongo1:2017,mongo2:2018,mongo3:2019/?replicaSet=replica_set_single");
        mongoClient = mongoConnection.getMongoClient();
    }

    @AfterEach
    public void tearDown() {
        compose.stop();
    }

    @Test
    public void testWithMongoServiceStopped(){
        ProductRepository repository = new ProductMongoDbRepository(mongoClient.getDatabase("KWIATEX"));
        ProductWithInfo product = RandomDataFactory.getRandomProductWithInfo();
        product.getProductInfo().setQuantity(5);
        repository.add(product);
        product = repository.findAll().getFirst();
        Assertions.assertEquals(5, repository.getById(product.getId()).getProductInfo().getQuantity());

        String containerId = compose.getContainerByServiceName("mongo1_1")
                .orElseThrow(() -> new IllegalStateException("Container mongo1_1 not found"))
                .getContainerId();
        dockerClient.stopContainerCmd(containerId).exec();

        Assertions.assertEquals(5, repository.getById(product.getId()).getProductInfo().getQuantity());
    }
}
