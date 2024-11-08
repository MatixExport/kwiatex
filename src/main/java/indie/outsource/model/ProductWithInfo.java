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
    @BsonProperty(value = "product",useDiscriminator = true)
    private Product product;
    @BsonProperty("productInfo")
    private ProductInfo productInfo;


    public ProductWithInfo(
           Product product,
           ProductInfo productInfo) {
        this.productInfo = productInfo;
        this.product = product;
    }

    @BsonCreator
    public ProductWithInfo(
            @BsonProperty("_id") UUID id,
            @BsonProperty("product") Product product,
            @BsonProperty("productInfo") ProductInfo productInfo) {
        super(id);
        this.productInfo = productInfo;
        this.product = product;
    }
}
