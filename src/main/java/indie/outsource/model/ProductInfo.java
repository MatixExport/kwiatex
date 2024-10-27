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
//    @BsonProperty("price")
//    private double price;


    @BsonCreator
    public ProductInfo(
            @BsonProperty("quantity") int quantity
//            @BsonProperty("price") double price
    ){
        this.quantity = quantity;
//        this.price = price;
    }
//    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE},fetch = FetchType.LAZY)
//    @MapsId
//    @JoinColumn(name = "product_id",referencedColumnName = "id")

//    public void setProduct(Product product) {
//        this.product = product;
//        product.setProductWithInfo(this);
//    }

}
