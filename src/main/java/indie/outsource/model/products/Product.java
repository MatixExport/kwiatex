package indie.outsource.model.products;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class Product {

    private int id;
    private String name;
    private float price;

    public abstract float calculateSellingPrice();

    public abstract String getProductInfo();


}
