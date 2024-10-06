package indie.outsource.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Access(AccessType.FIELD)
public class Client {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String surname;
    private String address;
    public Client(String name, String surname, String address) {
        this.name = name;
        this.surname = surname;
        this.address = address;
    }

    public String getClientInfo(){
        return "Name: " + name + " Surname: " + surname;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
