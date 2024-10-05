package indie.outsource.model.products;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Seeds extends Product {

    private int weight;
    private boolean edible;

    public Seeds(int id, String name, float price, int weight, boolean edible) {
        super(id, name, price);
        this.weight = weight;
        this.edible = edible;
    }

    @Override
    public String getProductInfo() {
        return getWeight()+ " bag of " + getName() + " seeds";
    }
}
