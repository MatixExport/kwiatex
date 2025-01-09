package indie.outsource.repositories.cassandra.transactions;

import com.datastax.oss.driver.api.core.ConsistencyLevel;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import indie.outsource.repositories.cassandra.products.ProductConsts;

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
                    .withColumn(ProductConsts.NAME, DataTypes.TEXT)
                    .withColumn(ProductConsts.COLOR, DataTypes.TEXT)
                    .withColumn(ProductConsts.GROWTH_STAGE, DataTypes.INT)
                    .withColumn(ProductConsts.PRICE, DataTypes.FLOAT)
                    .withColumn(ProductConsts.DISCRIMINATOR, DataTypes.TEXT)
                    .withColumn(ProductConsts.IS_EDIBLE, DataTypes.BOOLEAN)
                    .withColumn(ProductConsts.WEIGHT, DataTypes.INT)
                    .withColumn(ProductConsts.HEIGHT, DataTypes.INT)
                    .withColumn(ProductConsts.QUANTITY, DataTypes.INT)
                    .build();


    public static SimpleStatement dropTransactionsByClientTable = SimpleStatement
            .newInstance("drop table if exists transactions_by_client ;")
            .setConsistencyLevel(ConsistencyLevel.ALL);
    public static SimpleStatement dropTransactionsByIdTable = SimpleStatement
            .newInstance("drop table if exists transactions_by_id;")
            .setConsistencyLevel(ConsistencyLevel.ALL);
    public static SimpleStatement dropItemsByTransactionTable =SimpleStatement
            .newInstance("drop table if exists items_by_transaction;")
            .setConsistencyLevel(ConsistencyLevel.ALL);

    public static SimpleStatement truncateTransactionsByClientTable = SimpleStatement
            .newInstance("truncate table transactions_by_client ;")
            .setConsistencyLevel(ConsistencyLevel.ALL);
    public static SimpleStatement truncateTransactionsByIdTable = SimpleStatement
            .newInstance("truncate table transactions_by_id;")
            .setConsistencyLevel(ConsistencyLevel.ALL);

    public static SimpleStatement truncateItemsByTransactionTable =SimpleStatement
            .newInstance("truncate table items_by_transaction;")
            .setConsistencyLevel(ConsistencyLevel.ALL);

    public static BoundStatement prepareInsertTransaction(String tableName, CqlSession session) {
        SimpleStatement simpleInsert = QueryBuilder.insertInto(tableName)
                .value(TransactionConsts.TRANSACTION_ID, QueryBuilder.bindMarker(TransactionConsts.TRANSACTION_ID))
                .value(TransactionConsts.CLIENT_ID, QueryBuilder.bindMarker(TransactionConsts.CLIENT_ID))
                .build();

        PreparedStatement preparedInsert = session.prepare(simpleInsert);
        return preparedInsert.boundStatementBuilder()
                .setConsistencyLevel(ConsistencyLevel.ALL)
                .build();
    }

    public static BoundStatement prepareInsertItem(String tableName, CqlSession session) {
        SimpleStatement simpleInsert = QueryBuilder.insertInto(tableName)
                .value(TransactionConsts.ITEM_ID, QueryBuilder.bindMarker(TransactionConsts.ITEM_ID))
                .value(TransactionConsts.TRANSACTION_ID, QueryBuilder.bindMarker(TransactionConsts.TRANSACTION_ID))
                .value(TransactionConsts.PRODUCT_ID, QueryBuilder.bindMarker(TransactionConsts.PRODUCT_ID))
                .value(TransactionConsts.AMOUNT, QueryBuilder.bindMarker(TransactionConsts.AMOUNT))
                .value(ProductConsts.ID, QueryBuilder.bindMarker(ProductConsts.ID))
                .value(ProductConsts.NAME, QueryBuilder.bindMarker(ProductConsts.NAME))
                .value(ProductConsts.PRICE, QueryBuilder.bindMarker(ProductConsts.PRICE))
                .value(ProductConsts.QUANTITY, QueryBuilder.bindMarker(ProductConsts.QUANTITY))
                .value(ProductConsts.COLOR, QueryBuilder.bindMarker(ProductConsts.COLOR))
                .value(ProductConsts.IS_EDIBLE, QueryBuilder.bindMarker(ProductConsts.IS_EDIBLE))
                .value(ProductConsts.DISCRIMINATOR, QueryBuilder.bindMarker(ProductConsts.DISCRIMINATOR))
                .value(ProductConsts.WEIGHT, QueryBuilder.bindMarker(ProductConsts.WEIGHT))
                .value(ProductConsts.HEIGHT, QueryBuilder.bindMarker(ProductConsts.HEIGHT))
                .value(ProductConsts.GROWTH_STAGE, QueryBuilder.bindMarker(ProductConsts.GROWTH_STAGE))
                .build();

        PreparedStatement preparedInsert = session.prepare(simpleInsert);
        return preparedInsert.boundStatementBuilder()
                .setConsistencyLevel(ConsistencyLevel.ALL)
                .build();
    }
}
