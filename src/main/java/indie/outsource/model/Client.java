package indie.outsource.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Access(AccessType.FIELD)
public class Client extends AbstractEntity{

    private String name;
    private String surname;
    private String address;

    @OneToMany(mappedBy = "client",fetch = FetchType.LAZY)
    private List<Transaction> transactions = new ArrayList<>();

    public Client(String name, String surname, String address) {
        this.name = name;
        this.surname = surname;
        this.address = address;
    }

    public String getClientInfo(){
        return "Name: " + name + " Surname: " + surname;
    }
}
