package indie.outsource.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;


@Getter
@Setter
@Access(AccessType.FIELD)
public class Client extends AbstractEntity{

    @BsonProperty("name")
    private String name;
    @BsonProperty("surname")
    private String surname;
    @BsonProperty("address")
    private String address;

    @BsonCreator
    public Client(
            @BsonProperty("_id") UUID _id,
            @BsonProperty("name") String name,
            @BsonProperty("surname") String surname,
            @BsonProperty("address") String address
    ){
        super(_id);
        this.name = name;
        this.surname = surname;
        this.address = address;
    }


    public String getClientInfo(){
        return "Name: " + name + " Surname: " + surname;
    }
}
