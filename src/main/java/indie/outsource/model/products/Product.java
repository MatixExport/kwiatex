package indie.outsource.model.products;


import indie.outsource.model.AbstractEntity;
import indie.outsource.model.ProductWithInfo;
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

    @OneToOne(mappedBy = "product")
    private ProductWithInfo productWithInfo;

    public abstract float calculateSellingPrice();

    public abstract String getProductInfo();

    public Product(int id, String name, float price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
