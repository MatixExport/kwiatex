package indie.outsource.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Client {

    private int id;
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
}
