package indie.outsource.repositories.cassandra.transactions;


import com.datastax.oss.driver.api.core.cql.BatchStatement;
import com.datastax.oss.driver.api.core.cql.BatchType;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import indie.outsource.model.Transaction;
import indie.outsource.model.TransactionItem;
import indie.outsource.repositories.cassandra.BaseRepository;
import indie.outsource.repositories.cassandra.clients.ClientDao;
import indie.outsource.repositories.cassandra.clients.ClientMapper;
import indie.outsource.repositories.cassandra.clients.ClientMapperBuilder;

import java.util.List;

public class CassTransactionRepository extends BaseRepository {

    private final TransactionDao transactionDao;
    private final ClientDao clientDao;

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
        return new CassTransaction(transaction.getTransaction_id(), clientDao.findById(transaction.getTransaction_id()), transactionDao);
    }

    public List<Transaction> getTransactionByClientId(int clientId) {
        return transactionDao.findByClientId(clientId)
                .map(transaction ->{
                            return new CassTransaction(
                                    transaction.getTransaction_id(),
                                    clientDao.findById(transaction.getClient_id()),
                                    transactionDao)
                                    .toDomainModel();
                }

                ).toList();

    }

}
