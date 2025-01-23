package indie.outsource.documents.mappers;

import indie.outsource.documents.ShopTransactionDoc;
import indie.outsource.documents.TransactionItemDoc;
import indie.outsource.model.ShopTransaction;
import indie.outsource.model.TransactionItem;

import java.util.List;
import java.util.stream.Collectors;

public class ShopTransactionMapper {
    public static ShopTransaction toDomainModel(ShopTransactionDoc doc){
        return doc.toDomainModel();
    }
    public static ShopTransactionDoc fromDomainModel(ShopTransaction shopTransaction){
        return new ShopTransactionDoc(
                shopTransaction.getId(),
                ClientMapper.fromDomainModel(shopTransaction.getClient()),
                shopTransaction.getItems().stream()
                        .map(TransactionItemMapper::fromDomainModel)
                        .collect(Collectors.toList())

        );
    }

}
