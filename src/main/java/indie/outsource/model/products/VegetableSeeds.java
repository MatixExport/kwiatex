package indie.outsource.model.products;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class VegetableSeeds extends Seeds{


    public VegetableSeeds(int id, String name, float price, int weight, boolean edible) {
        super(id, name, price, weight, edible);
    }

    @Override
    public float calculateSellingPrice() {
        return getPrice();
    }

}
