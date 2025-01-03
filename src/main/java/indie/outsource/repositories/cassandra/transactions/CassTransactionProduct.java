package indie.outsource.repositories.cassandra.transactions;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import indie.outsource.model.ProductWithInfo;
import indie.outsource.model.TransactionItem;
import indie.outsource.model.products.Product;
import indie.outsource.repositories.cassandra.products.CassProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CassTransactionProduct extends CassProduct {
    @CqlName("item_id")
    private int transactionItemId;
    @CqlName("transaction_id")
    private int transactionId;
    private int amount;

    public CassTransactionProduct(TransactionItem transactionItem) {
        super(new ProductWithInfo(1, transactionItem.getProduct()));
        setTransactionItemId(transactionItem.getId());
        setAmount(transactionItem.getAmount());
    }
    public TransactionItem toTransactionItem() {
        Product product = toDomainModel().getProduct();
        return new TransactionItem(
            getTransactionItemId(),
            getAmount(),
            product,
            product.getPrice()
        );
    }

}
