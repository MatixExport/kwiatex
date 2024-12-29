package indie.outsource.repositories.cassandra.clients;

import com.datastax.oss.driver.api.mapper.annotations.*;

@Dao
public interface ClientByNameDao {
    @Insert
    void create(ClientByName client);
    @Query("SELECT * FROM "+ClientConsts.BY_NAME_TABLE_NAME+" WHERE name = :name")
    ClientByName findByName(String name);
    @Delete
    void delete(ClientByName client);
    @Update
    void update(ClientByName client);

}
