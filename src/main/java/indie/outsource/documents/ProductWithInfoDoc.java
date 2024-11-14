package indie.outsource.documents;

import indie.outsource.documents.products.ProductDoc;
import indie.outsource.model.AbstractEntity;
import indie.outsource.model.Client;
import indie.outsource.model.ProductWithInfo;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
public class ProductWithInfoDoc extends AbstractEntity implements Serializable {
    @BsonProperty(value = "product",useDiscriminator = true)
    private ProductDoc product;
    @BsonProperty("productInfo")
    private ProductInfoDoc productInfo;


    public ProductWithInfoDoc(
           ProductDoc product,
           ProductInfoDoc productInfo) {
        this.productInfo = productInfo;
        this.product = product;
    }

    @BsonCreator
    public ProductWithInfoDoc(
            @BsonProperty("_id") UUID id,
            @BsonProperty("product") ProductDoc product,
            @BsonProperty("productInfo") ProductInfoDoc productInfo) {
        super(id);
        this.productInfo = productInfo;
        this.product = product;
    }
    public ProductWithInfoDoc(UUID id){
        super(id);
    }

    public ProductWithInfo toDomainModel(){
        return new ProductWithInfo(
                this.getId(),
                this.getProduct().toDomainModel(),
                this.getProductInfo().toDomainModel()
        );
    }
}
