package indie.outsource.model.products;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrassesSeeds extends Seeds{

    public GrassesSeeds(int id, String name, float price, int weight, boolean edible) {
        super(id, name, price, weight, edible);
    }

    @Override
    public float calculateSellingPrice() {
        return getWeight() * getPrice();
    }


}
