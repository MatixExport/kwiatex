package indie.outsource.managers;

import indie.outsource.model.ProductWithInfo;
import indie.outsource.model.Transaction;
import indie.outsource.model.TransactionItem;
import indie.outsource.repositories.ProductRelationalRepository;
import indie.outsource.repositories.ProductRepository;
import indie.outsource.repositories.TransactionRelationalRepository;
import indie.outsource.repositories.TransactionRepository;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class ProductManager extends Manager {
    public ProductManager(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    public List<ProductWithInfo> findAllProducts() {
        ProductRepository repository = new ProductRelationalRepository(getEntityManager());
        return repository.findAll();
    }

    public boolean addProduct(ProductWithInfo product) {
        try{
            inSession((entityManager) -> {
                ProductRepository productRepository = new ProductRelationalRepository(entityManager);
                productRepository.add(product);
            });
        }
        catch(Exception e){
            return false;
        }
        return true;
    }

    public boolean finalizeTransaction(Transaction transaction) {
        try {
            inSession((entityManager) -> {
                TransactionRepository transactionRepository = new TransactionRelationalRepository(entityManager);
                ProductRepository productRepository = new ProductRelationalRepository(entityManager);

                for (TransactionItem transactionItem : transaction.getItems()) {
                    if(! productRepository.decreaseProductQuantity(
                            transactionItem.getProduct().getProductWithInfo(),
                            transactionItem.getAmount()))
                    {
                        throw new RuntimeException();
                    }
                }

                transactionRepository.add(transaction);
            });
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean increaseProductQuantity(ProductWithInfo product, int quantity) {
        try {
            inSession((entityManager) -> {
                ProductRepository productRepository = new ProductRelationalRepository(entityManager);
                productRepository.increaseProductQuantity(product, quantity);
            });
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
