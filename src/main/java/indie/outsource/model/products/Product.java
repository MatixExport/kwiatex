package indie.outsource.model.products;


import indie.outsource.model.AbstractEntity;
import indie.outsource.model.ProductInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.*;


@Setter
@Getter
@NoArgsConstructor
@BsonDiscriminator(key = "_clazz")
public class Product extends AbstractEntity{

    @BsonProperty("name")
    private String name ;

    @BsonProperty("price")
    private float price;

    @BsonIgnore
    public float calculateSellingPrice(){
        return 0;
    }

    @BsonIgnore
    public String getProductInfo(){
        return "";
    }


    @BsonCreator
    public Product(
            @BsonProperty("name") String name,
            @BsonProperty("price") float price) {
        this.name = name;
        this.price = price;
    }
}
