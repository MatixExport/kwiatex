package indie.outsource;

import indie.outsource.model.products.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class TransactionItem {
    private int id;
    private int amount;
    private Product product;
    private double price;

    public String getTransactionItemInfo(){
        return amount + "x " + product.getProductInfo() + " for: " + price;
    }
}
