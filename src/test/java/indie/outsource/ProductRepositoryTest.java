package indie.outsource;

import indie.outsource.model.ProductWithInfo;
import indie.outsource.model.products.Tree;
import indie.outsource.repositories.cassandra.ApplicationContext;
import indie.outsource.repositories.cassandra.products.CassProductRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ProductRepositoryTest {

    CassProductRepository productRepository;

    @Before
    public void setUp() {
        ApplicationContext applicationContext = ApplicationContext.getInstance();
        productRepository = applicationContext.getProductRepository();
    }

    @After
    public void tearDown() {
        productRepository.truncateTables();
    }

    @Test
    public void insertTest() {
        Tree tree = new Tree(5,"Dąb",15,2,2);
        productRepository.save(new ProductWithInfo(5,tree));

        assertEquals(tree.getName(), productRepository.getById(tree.getId()).getProduct().getName());
    }

    @Test
    public void deleteTest() {
        Tree tree = new Tree(10,"Dąb",15,2,2);
        productRepository.save(new ProductWithInfo(5,tree));
        productRepository.remove(tree.getId());
        assertNull(productRepository.getById(tree.getId()));
    }

    @Test
    public void updateTest() {
        Tree tree = new Tree(15,"Dąb",15,2,2);
        final String newName = "New Name";

        productRepository.save(new ProductWithInfo(5,tree));
        tree.setName(newName);

        productRepository.update(new ProductWithInfo(10,tree));

        assertEquals(10, productRepository.getById(tree.getId()).getQuantity());
        assertEquals(newName, productRepository.getById(tree.getId()).getProduct().getName());
    }
}
