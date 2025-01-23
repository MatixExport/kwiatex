package indie.outsource.model.products;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.*;

@Getter
@Setter
@NoArgsConstructor
public class GrassesSeeds extends Seeds{

    @BsonCreator
    public GrassesSeeds(String name,
           float price,
           int weight,
            boolean edible) {
           setName(name);
           setPrice(price);
           setWeight(weight);
           setEdible(edible);
    }

    @BsonIgnore
    @Override
    public float calculateSellingPrice() {
        return getWeight() * getPrice();
    }


}
