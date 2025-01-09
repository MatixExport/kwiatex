package indie.outsource.repositories.cassandra.transactions;


import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BatchStatement;
import com.datastax.oss.driver.api.core.cql.BatchType;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import indie.outsource.model.ProductWithInfo;
import indie.outsource.model.Transaction;
import indie.outsource.model.TransactionItem;
import indie.outsource.model.products.Product;
import indie.outsource.repositories.cassandra.ApplicationContext;
import indie.outsource.repositories.cassandra.BaseRepository;
import indie.outsource.repositories.cassandra.clients.CassClientRepository;
import indie.outsource.repositories.cassandra.products.CassProductRepository;
import indie.outsource.repositories.cassandra.products.ProductConsts;
import indie.outsource.repositories.cassandra.products.ProductStatementFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CassTransactionRepository extends BaseRepository {

    private final TransactionDao transactionDao;
    private final CassClientRepository clientRepository = ApplicationContext.getInstance().getCassClientRepository();

    BoundStatement insertIntoTransactionsByClient;
    BoundStatement insertIntoTransactionsById;
    BoundStatement insertIntoItemsByTransaction;
    BoundStatement updateProductQuantityById;

    public CassTransactionRepository(CqlSession session) {
        super(session);

        createTables();

        TransactionMapper transactionMapper = new TransactionMapperBuilder(getSession()).build();
        transactionDao = transactionMapper.getTransactionDao();

        insertIntoTransactionsByClient = TransactionStatementFactory.prepareInsertTransaction(TransactionConsts.BY_CLIENT_TABLE_NAME, getSession());
        insertIntoTransactionsById = TransactionStatementFactory.prepareInsertTransaction(TransactionConsts.BY_ID_TABLE_NAME, getSession());
        insertIntoItemsByTransaction = TransactionStatementFactory.prepareInsertItem(TransactionConsts.ITEMS_BY_TRANSACTION_TABLE_NAME, getSession());
        updateProductQuantityById = ProductStatementFactory.prepareUpdateQuantityById(getSession());
    }

    public void createTables() {
        getSession().execute(TransactionStatementFactory.createTransactionsByIdTable);
        getSession().execute(TransactionStatementFactory.createTransactionsByClientTable);
        getSession().execute(TransactionStatementFactory.createItemsByTransactionTable);
    }

    public void dropTables(){
        getSession().execute(TransactionStatementFactory.dropItemsByTransactionTable);
        getSession().execute(TransactionStatementFactory.dropTransactionsByIdTable);
        getSession().execute(TransactionStatementFactory.dropTransactionsByClientTable);
    }

    public void truncateTables(){
        getSession().execute(TransactionStatementFactory.truncateTransactionsByIdTable);
        getSession().execute(TransactionStatementFactory.truncateTransactionsByClientTable);
        getSession().execute(TransactionStatementFactory.truncateItemsByTransactionTable);
    }


    public boolean save(Transaction transaction) {
        CassProductRepository productRepository = ApplicationContext.getInstance().getProductRepository();
        ArrayList<ProductWithInfo> products = new ArrayList<>();
        for(TransactionItem item : transaction.getItems()) {
            products.add(productRepository.getById(item.getProduct().getId()));
        }

        BatchStatement batch = BatchStatement.newInstance(BatchType.LOGGED);
        for(int i = 0; i < products.size(); i++) {
            CassTransactionProduct product =  new CassTransactionProduct(transaction.getItems().get(i));
            product.setTransactionId(transaction.getId());
            batch = batch.add(
                    transactionDao.bind(product,insertIntoItemsByTransaction)
            );
            batch = batch.add(
                    updateProductQuantityById
                            .setInt(ProductConsts.QUANTITY, products.get(i).getQuantity()-product.getQuantity())
                            .setInt(ProductConsts.ID, product.getProductId())
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

        return getSession().execute(batch).wasApplied();
    }

    public Transaction getTransactionById(int id) {
        CassTransactionEntity transaction = transactionDao.findById(id);
        if(transaction == null) {
            return null;
        }
        return new CassTransaction(transaction.getTransactionId(), clientRepository.findById(transaction.getTransactionId()));
    }

    public List<Transaction> getTransactionByClientId(int clientId) {
        List<CassTransactionEntity> cassTransactions = transactionDao.findByClientId(clientId).toList();
        List<Transaction> transactions = new ArrayList<>();
        for (CassTransactionEntity cassTransactionEntity : cassTransactions) {
            transactions.add(
                    new CassTransaction(
                            cassTransactionEntity.getTransactionId(),
                            clientRepository.findById(cassTransactionEntity.getClientId()))
            );
        }

        return transactions;
    }

    public Stream<CassTransactionProduct> findItemsByTransactionId(int id) {
        return transactionDao.findItemsByTransactionId(id);
    }
}
