package indie.outsource.repositories.cassandra.clients;

import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Query;
import com.datastax.oss.driver.api.mapper.annotations.SetEntity;
import indie.outsource.model.Client;

import java.util.stream.Stream;

@Dao
public interface ClientDao {

    @SetEntity
    BoundStatement bind(Client client, BoundStatement boundStatement);

    @Query("SELECT * FROM " + ClientConsts.BY_ID_TABLE_NAME + " WHERE id = :id")
    Client findById(int id);

    @Query("SELECT * FROM " + ClientConsts.BY_NAME_TABLE_NAME + " WHERE name = :name")
    Stream<Client> findByName(String name);

}
