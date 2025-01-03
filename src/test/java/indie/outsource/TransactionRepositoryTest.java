package indie.outsource;

import indie.outsource.model.Client;
import indie.outsource.model.ProductWithInfo;
import indie.outsource.model.Transaction;
import indie.outsource.model.TransactionItem;
import indie.outsource.model.products.Tree;
import indie.outsource.repositories.cassandra.clients.ClientRepository;
import indie.outsource.repositories.cassandra.products.CassProductRepository;
import indie.outsource.repositories.cassandra.transactions.CassTransactionRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransactionRepositoryTest {

    ClientRepository clientRepository;
    CassTransactionRepository transactionRepository;
    CassProductRepository productRepository;

    @Before
    public void setUp() {
        clientRepository = new ClientRepository();
        transactionRepository = new CassTransactionRepository();
        productRepository = new CassProductRepository();
    }

    @Test
    public void insertTest() {
        Client client = new Client("Imie", "Nazwisko", 10, "al politechniki");
        clientRepository.save(client);

        Tree tree = new Tree(5,"Dąb",15,2,2);
        productRepository.save(new ProductWithInfo(5,tree));

        Transaction transaction = new Transaction(1, client);
        transaction.addTransactionItem(new TransactionItem(1, 1, tree, 15));

        transactionRepository.save(transaction);

        Transaction transaction1 = transactionRepository.getTransactionById(transaction.getId());

        assertEquals(1, transaction1.getItems().size());
        TransactionItem transactionItem = transaction1.getItems().getFirst();

        assertEquals(tree.getProductInfo(), transactionItem.getProduct().getProductInfo());

    }
}
