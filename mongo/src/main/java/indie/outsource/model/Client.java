package indie.outsource.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.UUID;


@Getter
@Setter
@Access(AccessType.FIELD)
public class Client extends AbstractEntity{

    private String name;
    private String surname;
    private String address;


    public Client(
          UUID _id,
          String name,
          String surname,
          String address
    ){
        super(_id);
        this.name = name;
        this.surname = surname;
        this.address = address;
    }

    @BsonIgnore
    public String getClientInfo(){
        return "Name: " + name + " Surname: " + surname;
    }
}
