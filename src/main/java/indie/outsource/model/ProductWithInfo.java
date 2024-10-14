package indie.outsource.model;

import indie.outsource.model.products.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductWithInfo extends AbstractEntity{
    private int quantity;
    private double price;

    @Id
    @GeneratedValue
    private int id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private Product product;

    public void setProduct(Product product) {
        this.product = product;
        product.setProductWithInfo(this);
    }

}
