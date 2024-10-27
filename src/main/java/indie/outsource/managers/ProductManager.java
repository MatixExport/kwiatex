package indie.outsource.managers;

import indie.outsource.model.ProductInfo;
import indie.outsource.model.ShopTransaction;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class ProductManager extends Manager {
    public ProductManager(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    public List<ProductInfo> findAllProducts() {
//        ProductRepository repository = new ProductRelationalRepository(getEntityManager());
//        return repository.findAll();
        return null;
    }

    public boolean addProduct(ProductInfo product) {
//        try{
//            inSession((entityManager) -> {
//                ProductRepository productRepository = new ProductRelationalRepository(entityManager);
//                productRepository.add(product);
//            });
//        }
//        catch(Exception e){
//            return false;
//        }
//        return true;
        return false;
    }

    public boolean finalizeTransaction(ShopTransaction shopTransaction) {
//        try {
//            inSession((entityManager) -> {
//                TransactionRepository transactionRepository = new TransactionRelationalRepository(entityManager);
//                ProductRepository productRepository = new ProductRelationalRepository(entityManager);
//
//                for (TransactionItem transactionItem : transaction.getItems()) {
//                    if(! productRepository.decreaseProductQuantity(
//                            transactionItem.getProduct().getProductWithInfo(),
//                            transactionItem.getAmount()))
//                    {
//                        throw new RuntimeException();
//                    }
//                }
//
//                transactionRepository.add(transaction);
//            });
//        } catch (Exception e) {
//            return false;
//        }
//        return true;
        return false;
    }

    public boolean increaseProductQuantity(ProductInfo product, int quantity) {
//        try {
//            inSession((entityManager) -> {
//                ProductRepository productRepository = new ProductRelationalRepository(entityManager);
//                productRepository.increaseProductQuantity(product, quantity);
//            });
//        } catch (Exception e) {
//            return false;
//        }
//        return true;
        return false;
    }

}
