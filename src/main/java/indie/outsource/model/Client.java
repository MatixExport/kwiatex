package indie.outsource.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Client {

    private String name;
    private String surname;
    private int id;
    private String address;

}
