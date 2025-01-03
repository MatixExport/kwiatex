package indie.outsource.model;

import indie.outsource.model.products.Product;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductWithInfo {
    private int quantity;
    private Product product;
}
