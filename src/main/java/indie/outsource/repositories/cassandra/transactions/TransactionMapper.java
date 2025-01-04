package indie.outsource.repositories.cassandra.transactions;

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;

@Mapper
public interface TransactionMapper {
    @DaoFactory
    TransactionDao getTransactionDao();
}
