package indie.outsource.model.products;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class Seeds extends Product {

    private int weight;
    private boolean edible;

    public Seeds(
            String name,
            float price,
            int weight,
            boolean edible) {
        super(name,price);
        this.weight = weight;
        this.edible = edible;
    }
    @Override
    public String getProductInfo() {
        return getWeight()+ " bag of " + getName() + " seeds";
    }
}
