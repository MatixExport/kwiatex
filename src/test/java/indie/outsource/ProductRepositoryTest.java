package indie.outsource;

import indie.outsource.model.ProductWithInfo;
import indie.outsource.model.products.Tree;
import indie.outsource.repositories.cassandra.products.CassProductRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ProductRepositoryTest {

    CassProductRepository repository;

    @Before
    public void setUp() {
        repository = new CassProductRepository();
    }

    @Test
    public void insertTest() {
        Tree tree = new Tree(5,"Dąb",15,2,2);
        repository.save(new ProductWithInfo(5,tree));

        assertEquals(tree.getName(),repository.getById(tree.getId()).getProduct().getName());
    }

    @Test
    public void deleteTest() {
        Tree tree = new Tree(10,"Dąb",15,2,2);
        repository.save(new ProductWithInfo(5,tree));
        repository.remove(tree.getId());
        assertNull(repository.getById(tree.getId()));
    }

    @Test
    public void updateTest() {
        Tree tree = new Tree(15,"Dąb",15,2,2);
        final String newName = "New Name";

        repository.save(new ProductWithInfo(5,tree));
        tree.setName(newName);

        repository.update(new ProductWithInfo(10,tree));

        assertEquals(10, repository.getById(tree.getId()).getQuantity());
        assertEquals(newName, repository.getById(tree.getId()).getProduct().getName());
    }
}
