package indie.outsource.model.products;


import indie.outsource.model.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public abstract class Product extends AbstractEntity{

    private String name ;

    private float price;

    public float calculateSellingPrice(){
        return 0;
    }

    public String getProductInfo(){
        return "";
    }


    public Product(
       String name,
       float price) {
        this.name = name;
        this.price = price;
    }
}
