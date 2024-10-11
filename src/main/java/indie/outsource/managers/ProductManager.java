package indie.outsource.managers;

import indie.outsource.exceptions.DatabaseException;
import indie.outsource.model.Client;
import indie.outsource.model.ProductWithInfo;
import indie.outsource.model.Transaction;
import indie.outsource.model.TransactionItem;
import indie.outsource.model.products.Product;
import indie.outsource.repositories.ProductRepository;
import indie.outsource.repositories.TransactionRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ProductManager {
    private ProductRepository productRepository;
    private TransactionRepository transactionRepository;

    public float getProductPrice(int productId){
        return productRepository.getById(productId).getProduct().getPrice();
    }

    public void decreaseProductAmount(int productId, int amount){
        ProductWithInfo product = productRepository.getById(productId);
        product.setQuantity(product.getQuantity() - amount);
        productRepository.add(product);
    }

    public int getProductAmount(int productId){
        ProductWithInfo product = productRepository.getById(productId);
        return product.getQuantity();
    }

    public ProductWithInfo getProductById(int productId){
        return productRepository.getById(productId);
    }

    public void removeProduct(ProductWithInfo product){productRepository.remove(product);}

    public void addProduct(ProductWithInfo product){
        productRepository.add(product);
    }



    public List<Transaction> getClientsTransactions(Client client) {
        return transactionRepository.getByClient(client);
    }

    private Transaction registerTransaction(Transaction transaction) {
        return transactionRepository.add(transaction);
    }


}
