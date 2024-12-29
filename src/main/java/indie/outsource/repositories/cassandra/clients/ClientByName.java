package indie.outsource.repositories.cassandra.clients;

import com.datastax.oss.driver.api.mapper.annotations.*;
import com.datastax.oss.driver.api.mapper.entity.naming.GetterStyle;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity(defaultKeyspace = "kwiatex")
@CqlName("clients_by_name")
@PropertyStrategy(mutable = false, getterStyle = GetterStyle.JAVABEANS)
public class ClientByName {

    @PartitionKey
    private String name;
    @ClusteringColumn
    private int id;
    private String surname;
    private String address;

    public ClientByName(String name, int id, String surname, String address) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
    }

}
