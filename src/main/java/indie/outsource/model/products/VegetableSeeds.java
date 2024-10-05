package indie.outsource.model.products;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VegetableSeeds extends Seeds{


    public VegetableSeeds(int id, String name, float price, int weight, boolean edible) {
        super(id, name, price, weight, edible);
    }

    @Override
    public float calculateSellingPrice() {
        return getPrice();
    }

}
