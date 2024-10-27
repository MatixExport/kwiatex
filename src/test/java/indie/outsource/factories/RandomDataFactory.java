package indie.outsource.factories;

import indie.outsource.model.Client;
import indie.outsource.model.ProductInfo;
import indie.outsource.model.ProductWithInfo;
import indie.outsource.model.ShopTransaction;
import indie.outsource.model.products.Tree;
import org.instancio.Instancio;

import static org.instancio.Select.field;

public class RandomDataFactory {

    private static int randomInt(int floor,int ceil){
        return (int)(Math.random() * (ceil-floor) + floor);
    }
    static public Client getRandomClient() {
        return Instancio.of(Client.class)
                .ignore(field(Client::getId))
                .create();
    }
    static public Tree getRandomProduct() {
        return getRandomProductOfClassType(Tree.class);
    }
    static public <T> T getRandomProductOfClassType(Class<T> classType) {
        return Instancio.of(classType)
//                .ignore(field(Product::getId))
                .create();
    }
    static public ProductInfo getRandomInfo() {
        return Instancio.of(ProductInfo.class)
                .create();
    }
    static public ProductWithInfo getRandomProductWithInfo() {
        return new ProductWithInfo(
                getRandomProduct(),
                getRandomInfo()
        );
    }
    static public ShopTransaction getRandomTransaction() {
        ShopTransaction shopTransaction =  Instancio.of(ShopTransaction.class)
//                .ignore(field(ShopTransaction::getId))
                .ignore(field(ShopTransaction::getClient))
                .ignore(field(ShopTransaction::getItems))

                .create();
        shopTransaction.setClient( getRandomClient());
        return shopTransaction;
    }
    static public ShopTransaction getRandomTransactionWithItems() {
        ShopTransaction shopTransaction = getRandomTransaction();
        for (int i = 0; i < randomInt(1,10); i++) {
            shopTransaction.addProduct(getRandomProduct(),randomInt(0,100));
        }
        return shopTransaction;
    }

}
