package indie.outsource.repositories.cassandra.clients;

import com.datastax.oss.driver.api.mapper.annotations.*;
import com.datastax.oss.driver.api.mapper.entity.naming.GetterStyle;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity(defaultKeyspace = "kwiatex")
@CqlName(ClientConsts.BY_ID_TABLE_NAME)
@PropertyStrategy(mutable = false, getterStyle = GetterStyle.JAVABEANS)
public class ClientById {

    @PartitionKey
    private int id;
    private String name;
    private String surname;
    private String address;

    public ClientById(int id, String name, String surname, String address) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
    }

}
