package indie.outsource.kafkaModel;

import indie.outsource.model.Client;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Access(AccessType.FIELD)
public class KfShopTransaction {

    private UUID id;
    private KfClient client;
    private List<KfTransactionItem> items = new ArrayList<KfTransactionItem>();

    public void addProduct(UUID productId, int quantity) {
        KfTransactionItem item = new KfTransactionItem(productId,quantity);
        items.add(item);
    }


    public KfShopTransaction(
            UUID id,
            KfClient client,
            List<KfTransactionItem> items) {
        this.id = id;
        this.client = client;
        this.items = items;
    }


    public void addTransactionItem(KfTransactionItem item){
        items.add(item);
    }
}
