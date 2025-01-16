package indie.outsource.documents.mappers;

import indie.outsource.documents.TransactionItemDoc;
import indie.outsource.model.TransactionItem;

public class TransactionItemMapper {
    public static TransactionItem toDomainModel(TransactionItemDoc doc){
        return doc.toDomainModel();
    }
    public static TransactionItemDoc fromDomainModel(TransactionItem item){
        return new TransactionItemDoc(
                ProductMapper.fromDomainModel(item.getProduct()),
                item.getAmount()
        );
    }
}
