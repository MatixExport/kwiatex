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
            Tree product = (Tree) domainProduct;
            return new TreeDoc(
                    product.getName(),
                    product.getPrice(),
                    product.getGrowthStage(),
                    product.getHeight()
            );
        }
        if (domainProduct.getClass().equals(Flower.class)) {
            Flower product = (Flower) domainProduct;
            return new FlowerDoc(
                    product.getName(),
                    product.getPrice(),
                    product.getGrowthStage(),
                    product.getColor()
            );

        }
        if (domainProduct.getClass().equals(GrassesSeeds.class)) {
            GrassesSeeds product = (GrassesSeeds) domainProduct;
            return new GrassesSeedsDoc(
                    product.getName(),
                    product.getPrice(),
                    product.getWeight(),
                    product.isEdible()
            );

        }
        VegetableSeeds product = (VegetableSeeds) domainProduct;
        return new VegetableSeedsDoc(
                product.getName(),
                product.getPrice(),
                product.getWeight(),
                product.isEdible()
        );
    }
}
