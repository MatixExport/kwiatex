package indie.outsource.documents;

import indie.outsource.model.ShopTransaction;
import indie.outsource.model.TransactionItem;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Access(AccessType.FIELD)
public class ShopTransactionDoc extends AbstractEntityDoc {

    @BsonProperty("client")
    private ClientDoc clientDoc;
    @BsonProperty("items")
    private List<TransactionItemDoc> items = new ArrayList<TransactionItemDoc>();

    @BsonCreator
    public ShopTransactionDoc(
           @BsonProperty("_id") UUID id,
           @BsonProperty("client") ClientDoc clientDoc,
           @BsonProperty("items") List<TransactionItemDoc> items) {
        super(id);
        this.clientDoc = clientDoc;
        this.items = items;
    }

    public ShopTransactionDoc(UUID id) {
        super(id);
    }

    public ShopTransaction toDomainModel(){
        List<TransactionItemDoc> transactionItemDocList;
        List<TransactionItem> transactionItems = new ArrayList<>();
        for (TransactionItemDoc item : items) {
            transactionItems.add(item.toDomainModel());
        }
        return new ShopTransaction(
                this.getId(),
                this.getClientDoc().toDomainModel(),
                transactionItems
        );
    }
}
