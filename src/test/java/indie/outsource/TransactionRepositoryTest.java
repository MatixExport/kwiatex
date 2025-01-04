package indie.outsource;

import indie.outsource.model.Client;
import indie.outsource.model.ProductWithInfo;
import indie.outsource.model.Transaction;
import indie.outsource.model.TransactionItem;
import indie.outsource.model.products.Tree;
import indie.outsource.repositories.cassandra.ApplicationContext;
import indie.outsource.repositories.cassandra.clients.ClientRepository;
import indie.outsource.repositories.cassandra.products.CassProductRepository;
import indie.outsource.repositories.cassandra.transactions.CassTransactionRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TransactionRepositoryTest {

    ClientRepository clientRepository;
    CassTransactionRepository transactionRepository;
    CassProductRepository productRepository;

    @Before
    public void setUp() {
        ApplicationContext applicationContext = ApplicationContext.getInstance();
        clientRepository = applicationContext.getClientRepository();
        transactionRepository = applicationContext.getTransactionRepository();
        productRepository = applicationContext.getProductRepository();
    }

    @After
    public void tearDown() {
        clientRepository.truncateTables();
        transactionRepository.truncateTables();
        productRepository.truncateTables();
    }

    @Test
    public void insertTest() {
        Client client = new Client("Imie", "Nazwisko", 10, "al politechniki");
        clientRepository.save(client);

        Tree tree = new Tree(5,"Dąb",15,2,2);
        productRepository.save(new ProductWithInfo(5,tree));

        Transaction transaction = new Transaction(2, client);
        transaction.addTransactionItem(new TransactionItem(1, 1, tree, 15));

        transactionRepository.save(transaction);

        Transaction transaction1 = transactionRepository.getTransactionById(transaction.getId());

        assertEquals(1, transaction1.getItems().size());
        TransactionItem transactionItem = transaction1.getItems().getFirst();

        assertEquals(tree.getProductInfo(), transactionItem.getProduct().getProductInfo());

    }
    @Test
    public void findByClientIdTest() {
        Client client = new Client("Imie", "Nazwisko", 10, "al politechniki");
        clientRepository.save(client);

        Client client1 = new Client("Imie", "Nazwisko", 15, "al politechniki");
        clientRepository.save(client);

        Tree tree = new Tree(5,"Dąb",15,2,2);
        productRepository.save(new ProductWithInfo(5,tree));

        Transaction transaction = new Transaction(1, client);
        transaction.addTransactionItem(new TransactionItem(11, 1, tree, 15));

        Transaction transaction2 = new Transaction(1, client1);
        transaction2.addTransactionItem(new TransactionItem(12, 1, tree, 30));

        transactionRepository.save(transaction);
        transactionRepository.save(transaction2);

        List<Transaction> transactions = transactionRepository.getTransactionByClientId(client.getId());
        assertEquals(1, transactions.size());
        assertEquals(
                transaction.getItems().getFirst().getPrice(),
                transactions.getFirst().getItems().getFirst().getPrice(),
                0.0001
        );
    }
}
