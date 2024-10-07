package indie.outsource;

import indie.outsource.model.ProductWithInfo;
import indie.outsource.model.Transaction;
import indie.outsource.model.products.Tree;
import indie.outsource.repositories.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import indie.outsource.model.Client;
import indie.outsource.repositories.ProductRelationalRepository;


public class TestTest {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");


    @Test
    void testAdd() {
        assertEquals(5, 5);
    }


    @Test
    void testManyMany(){
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Client client1 = new Client("John","Doe","6th Street");
        em.persist(client1);
        em.getTransaction().commit();


        em.getTransaction().begin();
        Tree tree = new Tree();
        tree.setHeight(20);
        tree.setName("wasd");
        tree.setPrice(20);
        tree.setGrowthStage(2);
        em.persist(tree);
        em.getTransaction().commit();

        em.getTransaction().begin();
        Transaction transaction = new Transaction();
        transaction.setClient(client1);
        transaction.addProduct(tree,5,20);
        em.persist(transaction);
        em.getTransaction().commit();


        em.getTransaction().begin();
        Transaction transaction2 = em.find(Transaction.class, transaction.getId());
        assertEquals(transaction2.getClient().getName(), client1.getName());
        assertEquals(transaction2.getItems().size(),1);
        assertEquals(transaction2.getItems().getFirst().getPrice(),20);
        em.getTransaction().commit();


    }
    @Test
    void testDb(){
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Client client1 = new Client("John","Doe","6th Street");
        em.persist(client1);
        em.getTransaction().commit();

        em.getTransaction().begin();
        Client client = em.find(Client.class, client1.getId());
        em.getTransaction().commit();
        assertEquals("John Doe", client.getName()+" "+client.getSurname());
    }
    @Test
    void repoTest(){
        EntityManager em = emf.createEntityManager();

        Tree tree = new Tree();
        tree.setHeight(20);
        tree.setName("wasd");
        tree.setPrice(20);
        tree.setGrowthStage(2);
        em.persist(tree);

        ProductRepository repository = new ProductRelationalRepository(em);
        ProductWithInfo product = new ProductWithInfo();
        product.setQuantity(10);
        product.setProduct(tree);
        repository.add(product);
        CriteriaQuery<ProductWithInfo> criteriaQuery = em.getCriteriaBuilder().createQuery(ProductWithInfo.class);
        criteriaQuery.from(ProductWithInfo.class);


        Query q = em.createQuery("Select e from ProductWithInfo e", ProductWithInfo.class);


        //assertEquals(1,em.createQuery(criteriaQuery).getResultList().size());
        assertEquals(1,q.getResultList().size());
        assertEquals(1,repository.getAll().size());
        assertEquals(repository.getAll().getFirst().getProduct().getName(), tree.getName());





    }
}
