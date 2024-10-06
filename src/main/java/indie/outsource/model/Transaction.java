package indie.outsource.model;

import indie.outsource.model.products.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Access(AccessType.FIELD)
@Table(name = "shop_transaction")
public class Transaction extends AbstractEntity {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(
            mappedBy = "transaction",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private final List<TransactionItem> items = new ArrayList<TransactionItem>();

    public void addProduct(Product product, int quantity,double price) {
        TransactionItem item = new TransactionItem(product,this,price,quantity);
        items.add(item);
    }

    public String getTransactionInfo(){
        StringBuilder info = new StringBuilder();
        info.append(client.getClientInfo()).append(" bought: /n");
        for(TransactionItem item : items){
            info.append(item.getTransactionItemInfo()).append("\n");
        }
        return info.toString();
    }

    public void addTransactionItem(TransactionItem item){
        items.add(item);
    }

}
