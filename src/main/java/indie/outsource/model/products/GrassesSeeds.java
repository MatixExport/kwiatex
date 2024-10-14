package indie.outsource.model.products;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class GrassesSeeds extends Seeds{

    public GrassesSeeds( String name, float price, int weight, boolean edible) {
        super( name, price, weight, edible);
    }

    @Override
    public float calculateSellingPrice() {
        return getWeight() * getPrice();
    }


}
