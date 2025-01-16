package indie.outsource.documents.mappers;

import indie.outsource.documents.ProductInfoDoc;
import indie.outsource.documents.ProductWithInfoDoc;
import indie.outsource.model.ProductWithInfo;

public class ProductWithInfoMapper {
    public static ProductWithInfo toDomainModel(ProductWithInfoDoc doc){
        return doc.toDomainModel();
    }
    public static ProductWithInfoDoc fromDomainModel(ProductWithInfo productWithInfo){
        return new ProductWithInfoDoc(
                productWithInfo.getId(),
                ProductMapper.fromDomainModel(productWithInfo.getProduct()),
                new ProductInfoDoc(productWithInfo.getProductInfo().getQuantity())
        );
    }
}
