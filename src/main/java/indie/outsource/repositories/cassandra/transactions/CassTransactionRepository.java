package indie.outsource.repositories.cassandra.transactions;


import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BatchStatement;
import com.datastax.oss.driver.api.core.cql.BatchType;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import indie.outsource.model.Transaction;
import indie.outsource.model.TransactionItem;
import indie.outsource.repositories.cassandra.ApplicationContext;
import indie.outsource.repositories.cassandra.BaseRepository;
import indie.outsource.repositories.cassandra.clients.CassClientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CassTransactionRepository extends BaseRepository {

    private final TransactionDao transactionDao;
    private final CassClientRepository clientRepository = ApplicationContext.getInstance().getCassClientRepository();

    BoundStatement insertIntoTransactionsByClient;
    BoundStatement insertIntoTransactionsById;
    BoundStatement insertIntoItemsByTransaction;

    public CassTransactionRepository(CqlSession session) {
        super(session);

        createTables();

        TransactionMapper transactionMapper = new TransactionMapperBuilder(getSession()).build();
        transactionDao = transactionMapper.getTransactionDao();

        insertIntoTransactionsByClient = TransactionStatementFactory.prepareInsertTransaction(TransactionConsts.BY_CLIENT_TABLE_NAME, getSession());
        insertIntoTransactionsById = TransactionStatementFactory.prepareInsertTransaction(TransactionConsts.BY_ID_TABLE_NAME, getSession());
        insertIntoItemsByTransaction = TransactionStatementFactory.prepareInsertItem(TransactionConsts.ITEMS_BY_TRANSACTION_TABLE_NAME, getSession());
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


    public void save(Transaction transaction) {
        BatchStatement batch = BatchStatement.newInstance(BatchType.LOGGED);

        for(TransactionItem item : transaction.getItems()) {
            CassTransactionProduct product =  new CassTransactionProduct(item);
            product.setTransactionId(transaction.getId());
            batch = batch.add(
                    transactionDao.bind(product,insertIntoItemsByTransaction)
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
