package indie.outsource.documents.mappers;

import indie.outsource.documents.ProductInfoDoc;
import indie.outsource.documents.ProductWithInfoDoc;
import indie.outsource.documents.products.*;
import indie.outsource.model.products.*;

public class ProductMapper {
    public static Product toDomainModel(ProductDoc doc){
        return doc.toDomainModel();
    }
    public static ProductDoc fromDomainModel(Product domainProduct){
        if(domainProduct.getClass().equals(Tree.class)){
            return new TreeDoc((Tree) domainProduct);
        }
        if (domainProduct.getClass().equals(Flower.class)) {
            return new FlowerDoc((Flower) domainProduct);
        }
        if (domainProduct.getClass().equals(GrassesSeeds.class)) {
            return new GrassesSeedsDoc((GrassesSeeds) domainProduct);
        }
        return new VegetableSeedsDoc((VegetableSeeds) domainProduct);
    }
}
