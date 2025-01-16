package indie.outsource.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ProductInfo{
    private int quantity;

    public ProductInfo(
            int quantity
    ){
        this.quantity = quantity;
    }

}
