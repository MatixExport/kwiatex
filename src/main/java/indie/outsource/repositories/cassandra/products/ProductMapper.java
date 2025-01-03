package indie.outsource.repositories.cassandra.products;

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;

@Mapper
public interface ProductMapper {

    @DaoFactory
    ProductDao getProductDao();

}
