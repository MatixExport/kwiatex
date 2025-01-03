package indie.outsource.repositories.cassandra.products;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.mapper.MapperContext;
import com.datastax.oss.driver.api.mapper.entity.EntityHelper;
import indie.outsource.model.ProductWithInfo;

import java.util.List;

public class ProductGetProvider {
    private final CqlSession session;
    private EntityHelper<CassProduct> helper;

    ProductGetProvider(MapperContext context,EntityHelper<CassProduct> helper) {
        session = context.getSession();
        this.helper = helper;
    }

    ProductWithInfo findProductById(Integer id) {
        Row row = session
            .execute(
            ProductStatementFactory.prepareSelectFromProductsById(session)
                    .setInt(ProductConsts.ID,id)
            )
            .one();
        return null;
    }

    List<ProductWithInfo> findAll() {
        return null;
    }




}
