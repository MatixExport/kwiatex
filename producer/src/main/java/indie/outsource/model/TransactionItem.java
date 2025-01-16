package indie.outsource.model;

import indie.outsource.model.products.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonIgnore;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
public class TransactionItem  implements Serializable {

    private Product product;
    private int amount;

    @BsonCreator
    public TransactionItem(
           Product product,
           int amount) {
        this.product = product;
        this.amount = amount;
    }

    @BsonIgnore
    public String getTransactionItemInfo(){
        return amount + "x " + product.getProductInfo() + " for: " + product.getPrice();
    }
}
