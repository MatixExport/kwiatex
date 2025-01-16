package indie.outsource.factories;

import indie.outsource.kafkaModel.KfShopTransaction;
import indie.outsource.model.Client;
import indie.outsource.model.ProductInfo;
import indie.outsource.model.ProductWithInfo;
import indie.outsource.model.ShopTransaction;
import indie.outsource.model.products.Tree;
import org.instancio.Instancio;

import java.util.UUID;

import static org.instancio.Select.field;

public class RandomDataFactory {

    private static int randomInt(int floor,int ceil){
        return (int)(Math.random() * (ceil-floor) + floor);
    }
    static public Client getRandomClient() {
        return Instancio.of(Client.class)
                .create();
    }
    static public Tree getRandomProduct() {
        return getRandomProductOfClassType(Tree.class);
    }
    static public <T> T getRandomProductOfClassType(Class<T> classType) {
        return Instancio.of(classType)
                .create();
    }
    static public ProductInfo getRandomInfo() {
        return Instancio.of(ProductInfo.class)
                .create();
    }
    static public ProductWithInfo getRandomProductWithInfo() {
        ProductWithInfo productWithInfo = Instancio.of(ProductWithInfo.class)
                .ignore(field(ProductWithInfo::getProduct))
                .ignore(field(ProductWithInfo::getProductInfo))
                .create();
        productWithInfo.setProduct(getRandomProduct());
        productWithInfo.setProductInfo(getRandomInfo());
        return productWithInfo;
    }
    static public ShopTransaction getRandomTransaction() {
        ShopTransaction shopTransaction =  Instancio.of(ShopTransaction.class)
                .ignore(field(ShopTransaction::getClient))
                .ignore(field(ShopTransaction::getItems))
                .create();
        shopTransaction.setClient( getRandomClient());
        return shopTransaction;
    }
    static public ShopTransaction getRandomTransactionWithItems() {
       return getRandomTransactionWithItems(randomInt(1,10));
    }
    static public ShopTransaction getRandomTransactionWithItems(int quantity) {
        ShopTransaction shopTransaction = getRandomTransaction();
        for (int i = 0; i < quantity; i++) {
            shopTransaction.addProduct(getRandomProduct(),randomInt(0,100));
        }
        return shopTransaction;
    }

    static public KfShopTransaction getRandomKfTransaction() {
        KfShopTransaction shopTransaction =  Instancio.of(KfShopTransaction.class)
                .ignore(field(ShopTransaction::getClient))
                .ignore(field(ShopTransaction::getItems))
                .create();
        shopTransaction.setClient(getRandomClient());
        return shopTransaction;
    }
    static public KfShopTransaction getRandomKfTransactionWithItems(int quantity) {
        KfShopTransaction shopTransaction = getRandomKfTransaction();
        for (int i = 0; i < quantity; i++) {
            shopTransaction.addProduct(UUID.randomUUID(),randomInt(0,100));
        }
        return shopTransaction;
    }






}
