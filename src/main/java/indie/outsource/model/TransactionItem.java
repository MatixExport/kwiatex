package indie.outsource.model;

import indie.outsource.model.products.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Entity
@Getter
@NoArgsConstructor
public class TransactionItem implements Serializable {

    @EmbeddedId
    private TransactionProductID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("transactionId")
    private Transaction transaction;


    private double price;
    private int amount;

    public TransactionItem(Product product, Transaction transaction, double price, int amount) {
        this.product = product;
        this.transaction = transaction;
        this.price = price;
        this.amount = amount;
        this.id = new TransactionProductID(product.getId(), transaction.getId());
    }

    public String getTransactionItemInfo(){
        return amount + "x " + product.getProductInfo() + " for: " + price;
    }
}
