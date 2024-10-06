package indie.outsource.model;

import indie.outsource.model.products.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductWithInfo {
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
