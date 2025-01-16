package indie.outsource.kafkaModel;

import indie.outsource.model.AbstractEntity;
import indie.outsource.model.Client;
import indie.outsource.model.TransactionItem;
import indie.outsource.model.products.Product;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Access(AccessType.FIELD)
public class KfShopTransaction {

    private UUID id;
    private Client client;
    private List<KfTransactionItem> items = new ArrayList<KfTransactionItem>();

    public void addProduct(UUID productId, int quantity) {
        KfTransactionItem item = new KfTransactionItem(productId,quantity);
        items.add(item);
    }


    public KfShopTransaction(
            UUID id,
            Client client,
            List<KfTransactionItem> items) {
        this.id = id;
        this.client = client;
        this.items = items;
    }


    public void addTransactionItem(KfTransactionItem item){
        items.add(item);
    }
}
