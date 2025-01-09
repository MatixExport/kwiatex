package indie.outsource.repositories.cassandra.clients;

import com.datastax.oss.driver.api.core.ConsistencyLevel;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;

public final class ClientStatementFactory {

    public static SimpleStatement createClientsByNameTable =
            SchemaBuilder.createTable(CqlIdentifier.fromCql(ClientConsts.BY_NAME_TABLE_NAME))
                    .ifNotExists()
                    .withPartitionKey(ClientConsts.NAME, DataTypes.TEXT)
                    .withClusteringColumn(ClientConsts.ID, DataTypes.INT)
                    .withColumn(ClientConsts.SURNAME, DataTypes.TEXT)
                    .withColumn(ClientConsts.ADDRESS, DataTypes.TEXT)
                    .build();

    public static SimpleStatement createClientsByIdTable =
            SchemaBuilder.createTable(CqlIdentifier.fromCql(ClientConsts.BY_ID_TABLE_NAME))
                    .ifNotExists()
                    .withPartitionKey(ClientConsts.ID, DataTypes.INT)
                    .withColumn(ClientConsts.SURNAME, DataTypes.TEXT)
                    .withColumn(ClientConsts.NAME, DataTypes.TEXT)
                    .withColumn(ClientConsts.ADDRESS, DataTypes.TEXT)
                    .build();

    public static SimpleStatement dropClientsByIdTable = SimpleStatement
            .newInstance("drop table if exists clients_by_id;")
            .setConsistencyLevel(ConsistencyLevel.ALL);
    public static SimpleStatement dropClientsByNameTable = SimpleStatement
            .newInstance("drop table if exists clients_by_name;")
            .setConsistencyLevel(ConsistencyLevel.ALL);


    public static SimpleStatement truncateClientsByIdTable = SimpleStatement
            .newInstance("truncate table clients_by_id;")
            .setConsistencyLevel(ConsistencyLevel.ALL);

    public static SimpleStatement truncateClientsByNameTable = SimpleStatement
            .newInstance("truncate table clients_by_name;")
            .setConsistencyLevel(ConsistencyLevel.ALL);

    public static BoundStatement prepareInsert(String tableName, CqlSession session) {
        SimpleStatement simpleInsert = QueryBuilder.insertInto(tableName)
                .value(ClientConsts.ID, QueryBuilder.bindMarker(ClientConsts.ID))
                .value(ClientConsts.NAME, QueryBuilder.bindMarker(ClientConsts.NAME))
                .value(ClientConsts.SURNAME, QueryBuilder.bindMarker(ClientConsts.SURNAME))
                .value(ClientConsts.ADDRESS, QueryBuilder.bindMarker(ClientConsts.ADDRESS))
                .usingTimestamp(QueryBuilder.bindMarker("timestamp"))
                .build();

        PreparedStatement preparedInsert = session.prepare(simpleInsert);
        return preparedInsert.boundStatementBuilder()
                .setConsistencyLevel(ConsistencyLevel.ALL)
                .build();
    }


    public static BoundStatement prepareUpdateClientsById(CqlSession session) {
        SimpleStatement simpleUpdate = QueryBuilder.update(ClientConsts.BY_ID_TABLE_NAME)
                .usingTimestamp(QueryBuilder.bindMarker("timestamp"))
                .setColumn(ClientConsts.NAME, QueryBuilder.bindMarker(ClientConsts.NAME))
                .setColumn(ClientConsts.SURNAME, QueryBuilder.bindMarker(ClientConsts.SURNAME))
                .setColumn(ClientConsts.ADDRESS, QueryBuilder.bindMarker(ClientConsts.ADDRESS))
                .whereColumn(ClientConsts.ID).isEqualTo(QueryBuilder.bindMarker(ClientConsts.ID))
                .build();

        PreparedStatement preparedUpdate = session.prepare(simpleUpdate);
        return preparedUpdate.boundStatementBuilder()
                .setConsistencyLevel(ConsistencyLevel.ALL)
                .build();
    }

    public static BoundStatement prepareDeleteFromClientsById(CqlSession session) {
        SimpleStatement simpleDelete = QueryBuilder.deleteFrom(ClientConsts.BY_ID_TABLE_NAME)
                .usingTimestamp(QueryBuilder.bindMarker("timestamp"))
                .whereColumn(ClientConsts.ID).isEqualTo(QueryBuilder.bindMarker(ClientConsts.ID))
                .build();

        PreparedStatement preparedDelete = session.prepare(simpleDelete);
        return preparedDelete.boundStatementBuilder()
                .setConsistencyLevel(ConsistencyLevel.ALL)
                .build();
    }

    public static BoundStatement prepareDeleteFromClientsByName(CqlSession session) {
        SimpleStatement simpleDelete = QueryBuilder.deleteFrom(ClientConsts.BY_NAME_TABLE_NAME)
                .usingTimestamp(QueryBuilder.bindMarker("timestamp"))
                .whereColumn(ClientConsts.NAME).isEqualTo(QueryBuilder.bindMarker(ClientConsts.NAME))
                .whereColumn(ClientConsts.ID).isEqualTo(QueryBuilder.bindMarker(ClientConsts.ID))
                .build();

        PreparedStatement preparedDelete = session.prepare(simpleDelete);
        return preparedDelete.boundStatementBuilder()
                .setConsistencyLevel(ConsistencyLevel.ALL)
                .build();
    }
}
