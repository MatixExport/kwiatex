package indie.outsource.kafkaModel;

import indie.outsource.model.products.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class KfTransactionItem  implements Serializable {

    private UUID productId;
    private int amount;

    @BsonCreator
    public KfTransactionItem(
            UUID productId,
            int amount) {
        this.productId = productId;
        this.amount = amount;
    }
}
