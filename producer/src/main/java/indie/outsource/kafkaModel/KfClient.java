package indie.outsource.kafkaModel;

import indie.outsource.model.AbstractEntity;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonIgnore;

import java.util.UUID;

@Getter
@Setter
@Access(AccessType.FIELD)
public class KfClient {

    private UUID id;
    private String name;
    private String surname;
    private String address;


    public KfClient(
            UUID _id,
            String name,
            String surname,
            String address
    ){
        this.id = _id;
        this.name = name;
        this.surname = surname;
        this.address = address;
    }

    @BsonIgnore
    public String getClientInfo(){
        return "Name: " + name + " Surname: " + surname;
    }
}
