package indie.outsource.model;

import indie.outsource.model.products.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Access(AccessType.FIELD)
public class ShopTransaction extends AbstractEntity {

    private Client client;
    private List<TransactionItem> items = new ArrayList<TransactionItem>();

    public void addProduct(Product product, int quantity) {
        TransactionItem item = new TransactionItem(product,quantity);
        items.add(item);
    }


    public ShopTransaction(
           UUID id,
           Client client,
           List<TransactionItem> items) {
        super(id);
        this.client = client;
        this.items = items;
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
