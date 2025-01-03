package indie.outsource.repositories.cassandra.products;

import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.mapper.annotations.*;

import java.util.stream.Stream;

@Dao
public interface ProductDao {

//    @QueryProvider(providerClass = ProductGetProvider.class,
//        entityHelpers = {CassProduct.class})
    @Query("SELECT * FROM " + ProductConsts.BY_ID_TABLE_NAME)
    Stream<CassProduct> findAll();

//    @QueryProvider(providerClass = ProductGetProvider.class,
//            entityHelpers = {CassProduct.class})
    @Query("SELECT * FROM " + ProductConsts.BY_ID_TABLE_NAME + " WHERE id = :id")
    CassProduct findProductById(Integer id);

    @SetEntity
    BoundStatement bind(CassProduct product, BoundStatement boundStatement);

}
