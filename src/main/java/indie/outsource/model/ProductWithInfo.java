package indie.outsource.model;

import indie.outsource.model.products.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductWithInfo {
    private int quantity;

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
