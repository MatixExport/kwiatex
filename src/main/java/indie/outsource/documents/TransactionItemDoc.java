package indie.outsource.documents;

import indie.outsource.documents.products.ProductDoc;
import indie.outsource.model.Client;
import indie.outsource.model.TransactionItem;
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
public class TransactionItemDoc implements Serializable {

    @BsonProperty(value = "product",useDiscriminator = true)
    private ProductDoc product;
    @BsonProperty("amount")
    private int amount;

    @BsonCreator
    public TransactionItemDoc(
           @BsonProperty("product") ProductDoc product,
           @BsonProperty("amount") int amount) {
        this.product = product;
        this.amount = amount;
    }

    public TransactionItem toDomainModel(){
        return new TransactionItem(
                this.getProduct().toDomainModel(),
                this.getAmount()
        );
    }

}
