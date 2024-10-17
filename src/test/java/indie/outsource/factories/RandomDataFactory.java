package indie.outsource.factories;

import indie.outsource.model.Client;
import indie.outsource.model.ProductWithInfo;
import indie.outsource.model.Transaction;
import indie.outsource.model.products.GrassesSeeds;
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
                .ignore(field(Client::getTransactions))
                .ignore(field(Client::getVersion))
                .create();
    }
    static public Tree getRandomProduct() {
        return getRandomProductOfClassType(Tree.class);
    }
    static public <T> T getRandomProductOfClassType(Class<T> classType) {
        return Instancio.of(classType)
                .ignore(field(Tree::getId))
                .ignore(field(Tree::getProductWithInfo))
                .ignore(field(Tree::getVersion))
                .create();
    }
    static public ProductWithInfo getRandomProductWithInfo() {
        ProductWithInfo productWithInfo = Instancio.of(ProductWithInfo.class)
                .ignore(field(ProductWithInfo::getId))
                .ignore(field(ProductWithInfo::getProduct))
                .ignore(field(ProductWithInfo::getVersion))
                .create();
        productWithInfo.setProduct(getRandomProduct());
        return productWithInfo;
    }
    static public Transaction getRandomTransaction() {
        Transaction transaction =  Instancio.of(Transaction.class)
                .ignore(field(Transaction::getId))
                .ignore(field(Transaction::getClient))
                .ignore(field(Transaction::getItems))
                .ignore(field(Transaction::getVersion))
                .create();
        transaction.setClient( getRandomClient());
        return transaction;
    }
    static public Transaction getRandomTransactionWithItems() {
        Transaction transaction = getRandomTransaction();
        for (int i = 0; i < randomInt(1,10); i++) {
            transaction.addProduct(getRandomProduct(),randomInt(0,100),randomInt(0,100));
        }
        return transaction;
    }

}
