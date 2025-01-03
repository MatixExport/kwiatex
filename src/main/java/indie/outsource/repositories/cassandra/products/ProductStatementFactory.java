package indie.outsource.repositories.cassandra.products;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;

public final class ProductStatementFactory {

    public static SimpleStatement createProductsByIdTable =
            SchemaBuilder.createTable(CqlIdentifier.fromCql(ProductConsts.BY_ID_TABLE_NAME))
                    .ifNotExists()
                    .withPartitionKey(ProductConsts.ID, DataTypes.INT)
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


    public static BoundStatement prepareInsert(CqlSession session) {
        SimpleStatement simpleInsert = QueryBuilder.insertInto(ProductConsts.BY_ID_TABLE_NAME)
                .value(ProductConsts.ID, QueryBuilder.bindMarker(ProductConsts.ID))
                .value(ProductConsts.NAME, QueryBuilder.bindMarker(ProductConsts.NAME))
                .value(ProductConsts.QUANTITY, QueryBuilder.bindMarker(ProductConsts.QUANTITY))
                .value(ProductConsts.COLOR, QueryBuilder.bindMarker(ProductConsts.COLOR))
                .value(ProductConsts.PRICE, QueryBuilder.bindMarker(ProductConsts.PRICE))
                .value(ProductConsts.IS_EDIBLE, QueryBuilder.bindMarker(ProductConsts.IS_EDIBLE))
                .value(ProductConsts.DISCRIMINATOR, QueryBuilder.bindMarker(ProductConsts.DISCRIMINATOR))
                .value(ProductConsts.WEIGHT, QueryBuilder.bindMarker(ProductConsts.WEIGHT))
                .value(ProductConsts.HEIGHT, QueryBuilder.bindMarker(ProductConsts.HEIGHT))
                .value(ProductConsts.GROWTH_STAGE, QueryBuilder.bindMarker(ProductConsts.GROWTH_STAGE))
                .build();

        PreparedStatement preparedInsert = session.prepare(simpleInsert);
        return preparedInsert.boundStatementBuilder().build();
    }

    public static BoundStatement prepareUpdateProductById(CqlSession session) {
        SimpleStatement simpleUpdate = QueryBuilder.update(ProductConsts.BY_ID_TABLE_NAME)
                .setColumn(ProductConsts.NAME, QueryBuilder.bindMarker(ProductConsts.NAME))
                .setColumn(ProductConsts.QUANTITY, QueryBuilder.bindMarker(ProductConsts.QUANTITY))
                .setColumn(ProductConsts.COLOR, QueryBuilder.bindMarker(ProductConsts.COLOR))
                .setColumn(ProductConsts.PRICE, QueryBuilder.bindMarker(ProductConsts.PRICE))
                .setColumn(ProductConsts.IS_EDIBLE, QueryBuilder.bindMarker(ProductConsts.IS_EDIBLE))
                .setColumn(ProductConsts.DISCRIMINATOR, QueryBuilder.bindMarker(ProductConsts.DISCRIMINATOR))
                .setColumn(ProductConsts.WEIGHT, QueryBuilder.bindMarker(ProductConsts.WEIGHT))
                .setColumn(ProductConsts.HEIGHT, QueryBuilder.bindMarker(ProductConsts.HEIGHT))
                .setColumn(ProductConsts.GROWTH_STAGE, QueryBuilder.bindMarker(ProductConsts.GROWTH_STAGE))
                .whereColumn(ProductConsts.ID).isEqualTo(QueryBuilder.bindMarker(ProductConsts.ID))
                .build();

        PreparedStatement preparedUpdate = session.prepare(simpleUpdate);
        return preparedUpdate.boundStatementBuilder().build();
    }

    public static BoundStatement prepareDeleteFromProductsById(CqlSession session) {
        SimpleStatement simpleDelete = QueryBuilder.deleteFrom(ProductConsts.BY_ID_TABLE_NAME)
                .whereColumn(ProductConsts.ID).isEqualTo(QueryBuilder.bindMarker(ProductConsts.ID))
                .build();

        PreparedStatement preparedDelete = session.prepare(simpleDelete);
        return preparedDelete.boundStatementBuilder().build();
    }

    public static BoundStatement prepareSelectFromProductsById(CqlSession session) {
        SimpleStatement select = QueryBuilder
                .selectFrom(ProductConsts.BY_ID_TABLE_NAME)
                .all()
                .where(Relation.column(ProductConsts.ID).isEqualTo(QueryBuilder.bindMarker(ProductConsts.ID)))
                .build();
        PreparedStatement preparedSelect = session.prepare(select);
        return preparedSelect.boundStatementBuilder().build();
    }

    public static BoundStatement prepareSelectFromProducts(CqlSession session) {
        SimpleStatement select = QueryBuilder
                .selectFrom(ProductConsts.BY_ID_TABLE_NAME)
                .all()
                .build();
        PreparedStatement preparedSelect = session.prepare(select);
        return preparedSelect.boundStatementBuilder().build();
    }
}

