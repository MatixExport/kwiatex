package indie.outsource.model;

import lombok.*;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;


@Getter
@Setter
@NoArgsConstructor
public class ProductInfo{
    private int quantity;

    public ProductInfo(
            int quantity
    ){
        this.quantity = quantity;
    }

}
