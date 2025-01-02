package indie.outsource.repositories.cassandra.clients;

import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.mapper.annotations.*;

@Dao
public interface ClientByIdDao {

    @Insert
    void create(ClientById client);
    @Query("SELECT * FROM "+ClientConsts.BY_ID_TABLE_NAME+" WHERE id = :id")
    ClientById findById(int id);
    @Delete
    void delete(ClientById client);
    @Update
    void update(ClientById client);

    @SetEntity
    BoundStatement bind(ClientById client, BoundStatement boundStatement);
}
