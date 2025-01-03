package indie.outsource.repositories.cassandra.transactions;

import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Query;
import com.datastax.oss.driver.api.mapper.annotations.SetEntity;

import java.util.stream.Stream;

@Dao
public interface TransactionDao {

    @SetEntity
    BoundStatement bind(CassTransactionEntity transaction, BoundStatement boundStatement);

    @SetEntity
    BoundStatement bind(CassTransactionProduct transactionItem, BoundStatement boundStatement);

    @Query("SELECT * FROM " + TransactionConsts.BY_ID_TABLE_NAME + " WHERE transaction_id = :id")
    CassTransactionEntity findById(int id);

    @Query("SELECT * FROM " + TransactionConsts.BY_CLIENT_TABLE_NAME + " WHERE client_id = :id")
    Stream<CassTransactionEntity> findByClientId(int id);

    @Query("SELECT * FROM " + TransactionConsts.ITEMS_BY_TRANSACTION_TABLE_NAME + " WHERE transaction_id = :transactionId")
    Stream<CassTransactionProduct> findItemsByTransactionId(int transactionId);
}
