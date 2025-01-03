package indie.outsource.repositories.cassandra.transactions;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;

public final class TransactionStatementFactory {

    public static SimpleStatement createTransactionsByClientTable =
            SchemaBuilder.createTable(CqlIdentifier.fromCql(TransactionConsts.BY_CLIENT_TABLE_NAME))
                    .ifNotExists()
                    .withPartitionKey(TransactionConsts.CLIENT_ID, DataTypes.INT)
                    .withClusteringColumn(TransactionConsts.TRANSACTION_ID, DataTypes.INT)
                    .build();

    public static SimpleStatement createTransactionsByIdTable =
            SchemaBuilder.createTable(CqlIdentifier.fromCql(TransactionConsts.BY_ID_TABLE_NAME))
                    .ifNotExists()
                    .withPartitionKey(TransactionConsts.TRANSACTION_ID, DataTypes.INT)
                    .withColumn(TransactionConsts.CLIENT_ID, DataTypes.INT)
                    .build();

    public static SimpleStatement createItemsByTransactionTable =
            SchemaBuilder.createTable(CqlIdentifier.fromCql(TransactionConsts.ITEMS_BY_TRANSACTION_TABLE_NAME))
                    .ifNotExists()
                    .withPartitionKey(TransactionConsts.TRANSACTION_ID, DataTypes.INT)
                    .withClusteringColumn(TransactionConsts.ITEM_ID, DataTypes.INT)
                    .withColumn(TransactionConsts.PRODUCT_ID, DataTypes.INT)
                    .withColumn(TransactionConsts.AMOUNT, DataTypes.INT)
                    .withColumn(TransactionConsts.PRICE, DataTypes.DOUBLE)
                    .build();


    public static BoundStatement prepareInsertTransaction(String tableName, CqlSession session) {
        SimpleStatement simpleInsert = QueryBuilder.insertInto(tableName)
                .value(TransactionConsts.TRANSACTION_ID, QueryBuilder.bindMarker(TransactionConsts.TRANSACTION_ID))
                .value(TransactionConsts.CLIENT_ID, QueryBuilder.bindMarker(TransactionConsts.CLIENT_ID))
                .build();

        PreparedStatement preparedInsert = session.prepare(simpleInsert);
        return preparedInsert.boundStatementBuilder().build();
    }

    public static BoundStatement prepareInsertItem(String tableName, CqlSession session) {
        SimpleStatement simpleInsert = QueryBuilder.insertInto(tableName)
                .value(TransactionConsts.ITEM_ID, QueryBuilder.bindMarker(TransactionConsts.ITEM_ID))
                .value(TransactionConsts.TRANSACTION_ID, QueryBuilder.bindMarker(TransactionConsts.TRANSACTION_ID))
                .value(TransactionConsts.PRODUCT_ID, QueryBuilder.bindMarker(TransactionConsts.PRODUCT_ID))
                .value(TransactionConsts.AMOUNT, QueryBuilder.bindMarker(TransactionConsts.AMOUNT))
                .value(TransactionConsts.PRICE, QueryBuilder.bindMarker(TransactionConsts.PRICE))
                .build();

        PreparedStatement preparedInsert = session.prepare(simpleInsert);
        return preparedInsert.boundStatementBuilder().build();
    }
}
