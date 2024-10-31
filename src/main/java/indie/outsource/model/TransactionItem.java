package indie.outsource.model;

import indie.outsource.model.products.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
public class TransactionItem  implements Serializable {

    @BsonProperty(value = "product",useDiscriminator = true)
    private Product product;
    @BsonProperty("amount")
    private int amount;

    @BsonCreator
    public TransactionItem(
           @BsonProperty("product") Product product,
           @BsonProperty("amount") int amount) {
        this.product = product;
        this.amount = amount;
    }

    @BsonIgnore
    public String getTransactionItemInfo(){
        return amount + "x " + product.getProductInfo() + " for: " + product.getPrice();
    }
}
