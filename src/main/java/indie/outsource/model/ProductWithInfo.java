package indie.outsource.model;

import indie.outsource.model.products.Product;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Setter
@Getter
public class ProductWithInfo {
    @BsonProperty("product")
    private Product product;
    @BsonProperty("productInfo")
    private ProductInfo productInfo;
    @BsonCreator
    public ProductWithInfo(
            @BsonProperty("product") Product product,
            @BsonProperty("productInfo") ProductInfo productInfo) {
        this.productInfo = productInfo;
        this.product = product;
    }
}
