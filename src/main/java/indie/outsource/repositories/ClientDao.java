package indie.outsource.repositories;

import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.Query;
import com.datastax.oss.protocol.internal.response.Result;
import indie.outsource.model.Client;

import java.util.List;

@Dao
public interface ClientDao {
    @Insert
    void create(Client client);
//    @Query("SELECT * FROM clients WHERE name = :name")
//    Result findByName(String name);

}
