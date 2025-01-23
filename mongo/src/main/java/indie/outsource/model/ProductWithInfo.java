package indie.outsource.model;

import indie.outsource.model.products.Product;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
public class ProductWithInfo extends AbstractEntity implements Serializable {
    private Product product;
    private ProductInfo productInfo;


    public ProductWithInfo(
           Product product,
           ProductInfo productInfo) {
        this.productInfo = productInfo;
        this.product = product;
    }

    public ProductWithInfo(
          UUID id,
          Product product,
          ProductInfo productInfo) {
        super(id);
        this.productInfo = productInfo;
        this.product = product;
    }
}
