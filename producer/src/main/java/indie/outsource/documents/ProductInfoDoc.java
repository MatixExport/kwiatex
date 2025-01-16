package indie.outsource.documents;

import indie.outsource.model.ProductInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;


@Getter
@Setter
@NoArgsConstructor
public class ProductInfoDoc {
    @BsonProperty("quantity")
    private int quantity;

    @BsonCreator
    public ProductInfoDoc(
            @BsonProperty("quantity") int quantity
    ){
        this.quantity = quantity;
    }

    public ProductInfo toDomainModel(){
        return new ProductInfo(
                this.getQuantity()
        );
    }

}
