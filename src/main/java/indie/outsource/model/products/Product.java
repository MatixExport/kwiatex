package indie.outsource.model.products;


import indie.outsource.model.AbstractEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Access(AccessType.FIELD)
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public abstract class Product extends AbstractEntity {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private float price;

    public abstract float calculateSellingPrice();

    public abstract String getProductInfo();


}
