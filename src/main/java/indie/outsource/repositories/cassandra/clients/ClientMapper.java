package indie.outsource.repositories.cassandra.clients;

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;

@Mapper
public interface ClientMapper {

    @DaoFactory
    ClientByNameDao getClientByNameDao();

    @DaoFactory
    ClientByIdDao getClientByIdDao();
}
