package indie.outsource.repositories.cassandra.transactions;


import com.datastax.oss.driver.api.core.cql.BatchStatement;
import com.datastax.oss.driver.api.core.cql.BatchType;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import indie.outsource.model.Transaction;
import indie.outsource.model.TransactionItem;
import indie.outsource.repositories.ProductRepository;
import indie.outsource.repositories.cassandra.BaseRepository;
import indie.outsource.repositories.cassandra.clients.ClientDao;
import indie.outsource.repositories.cassandra.clients.ClientMapper;
import indie.outsource.repositories.cassandra.clients.ClientMapperBuilder;
import indie.outsource.repositories.cassandra.products.CassProductRepository;

public class CassTransactionRepository extends BaseRepository {

    private final TransactionDao transactionDao;
    private final ClientDao clientDao;
    private final ProductRepository productRepository;

    BoundStatement insertIntoTransactionsByClient;
    BoundStatement insertIntoTransactionsById;
    BoundStatement insertIntoItemsByTransaction;

    public CassTransactionRepository() {
        super();

        createTransactionTables();

        TransactionMapper transactionMapper = new TransactionMapperBuilder(getSession()).build();
        transactionDao = transactionMapper.getTransactionDao();

        ClientMapper clientMapper = new ClientMapperBuilder(getSession()).build();
        clientDao = clientMapper.getClientDao();

        productRepository = new CassProductRepository();

        insertIntoTransactionsByClient = TransactionStatementFactory.prepareInsertTransaction(TransactionConsts.BY_CLIENT_TABLE_NAME, getSession());
        insertIntoTransactionsById = TransactionStatementFactory.prepareInsertTransaction(TransactionConsts.BY_ID_TABLE_NAME, getSession());
        insertIntoItemsByTransaction = TransactionStatementFactory.prepareInsertItem(TransactionConsts.ITEMS_BY_TRANSACTION_TABLE_NAME, getSession());
    }

    private void createTransactionTables() {
        getSession().execute(TransactionStatementFactory.createTransactionsByIdTable);
        getSession().execute(TransactionStatementFactory.createTransactionsByClientTable);
        getSession().execute(TransactionStatementFactory.createItemsByTransactionTable);
    }


    public void save(Transaction transaction) {
        BatchStatement batch = BatchStatement.newInstance(BatchType.LOGGED);

        for(TransactionItem item : transaction.getItems()) {
            batch = batch.add(
                    insertIntoItemsByTransaction
                            .setInt(TransactionConsts.TRANSACTION_ID, transaction.getId())
                            .setInt(TransactionConsts.ITEM_ID, item.getId())
                            .setInt(TransactionConsts.AMOUNT, item.getAmount())
                            .setDouble(TransactionConsts.PRICE, item.getPrice())
                            .setInt(TransactionConsts.PRODUCT_ID, item.getProduct().getId())
            );
        }
        batch = batch.add(
                insertIntoTransactionsByClient
                        .setInt(TransactionConsts.TRANSACTION_ID, transaction.getId())
                        .setInt(TransactionConsts.CLIENT_ID, transaction.getClient().getId())
        );
        batch = batch.add(
                insertIntoTransactionsById
                        .setInt(TransactionConsts.TRANSACTION_ID, transaction.getId())
                        .setInt(TransactionConsts.CLIENT_ID, transaction.getClient().getId())
        );
        getSession().execute(batch);
    }

    public Transaction getTransactionById(int id) {
        CassTransactionEntity transaction = transactionDao.findById(id);
        if(transaction == null) {
            return null;
        }
        return new CassTransaction(transaction.getTransaction_id(), clientDao.findById(transaction.getTransaction_id()), transactionDao, productRepository);
    }

}
