package indie.outsource.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.UUID;


@Getter
@Setter
@Access(AccessType.FIELD)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Client extends AbstractEntity{

    private String name;
    private String surname;
    private String address;


    @BsonCreator
    public Client(
          UUID id,
          String name,
          String surname,
          String address
    ){
        super(id);
        this.name = name;
        this.surname = surname;
        this.address = address;
    }

    @JsonIgnore
    @BsonIgnore
    public String getClientInfo(){
        return "Name: " + name + " Surname: " + surname;
    }
}
