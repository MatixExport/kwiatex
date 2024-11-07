package indie.outsource.model;

import indie.outsource.model.products.Product;
import lombok.*;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;


@Getter
@Setter
@NoArgsConstructor
public class ProductInfo{
    @BsonProperty("quantity")
    private int quantity;

    @BsonCreator
    public ProductInfo(
            @BsonProperty("quantity") int quantity
    ){
        this.quantity = quantity;
    }

}
