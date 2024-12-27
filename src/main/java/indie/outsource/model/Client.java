package indie.outsource.model;

import com.datastax.oss.driver.api.mapper.annotations.*;
import com.datastax.oss.driver.api.mapper.entity.naming.GetterStyle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//@AllArgsConstructor
@Getter
@Setter
@Entity(defaultKeyspace = "kwiatex")
@CqlName("clients_by_name")
@PropertyStrategy(mutable = false, getterStyle = GetterStyle.JAVABEANS)
public class Client {
//
//    @PartitionKey

    @PartitionKey
    private String name;
//    @ClusteringColumn()
    private String surname;
    private int id;
    private String address;


    public Client(java.lang.String name, java.lang.String surname, int id, java.lang.String address) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
    }

}
