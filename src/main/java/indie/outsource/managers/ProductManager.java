package indie.outsource.managers;

import indie.outsource.model.ProductWithInfo;
import indie.outsource.repositories.ProductRepository;

public class ProductManager {
    private ProductRepository productRepository;            // needs to be injected

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

    public void removeProductById(int productId){
        productRepository.remove(productId);
    }

    public void addProduct(ProductWithInfo product){
        productRepository.add(product);
    }

}
