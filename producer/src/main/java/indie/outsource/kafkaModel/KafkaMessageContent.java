package indie.outsource.kafkaModel;

import indie.outsource.documents.ShopTransactionDoc;
import indie.outsource.model.ShopTransaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;

@Getter
@Setter
@NoArgsConstructor
public class KafkaMessageContent {
    private String storeName;
    private ShopTransactionDoc transaction;

    @BsonCreator
    public KafkaMessageContent(String storeName, ShopTransactionDoc transaction) {
        this.storeName = storeName;
        this.transaction = transaction;
    }
}
