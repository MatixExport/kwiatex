package indie.outsource.documents;

import indie.outsource.model.Client;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;


@Getter
@Setter
@Access(AccessType.FIELD)
public class ClientDoc extends AbstractEntityDoc {

    @BsonProperty("name")
    private String name;
    @BsonProperty("surname")
    private String surname;
    @BsonProperty("address")
    private String address;

    @BsonCreator
    public ClientDoc(
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
    public ClientDoc(UUID _id){
        super(_id);
    }

    public Client toDomainModel(){
        return new Client(
                this.getId(),
                this.getName(),
                this.getSurname(),
                this.getAddress()
        );
    }

}
