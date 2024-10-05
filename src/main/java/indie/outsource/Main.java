package indie.outsource;

import indie.outsource.model.products.Flower;
import indie.outsource.model.products.Product;

public class Main {
    public static void main(String[] args) {
        Product p1 = new Flower(1,"rose", 10.2F, 1, "red");
        System.out.println(p1.getProductInfo());
    }
}