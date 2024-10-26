package indie.outsource.model;

import indie.outsource.model.products.Product;
import lombok.*;
import org.bson.codecs.pojo.annotations.BsonProperty;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfo extends AbstractEntity{
    @BsonProperty
    private int quantity;
    @BsonProperty
    private double price;

//    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE},fetch = FetchType.LAZY)
//    @MapsId
//    @JoinColumn(name = "product_id",referencedColumnName = "id")

//    public void setProduct(Product product) {
//        this.product = product;
//        product.setProductWithInfo(this);
//    }

}
