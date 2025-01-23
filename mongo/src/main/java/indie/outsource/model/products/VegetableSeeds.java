package indie.outsource.model.products;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Getter
@Setter
@NoArgsConstructor
public class VegetableSeeds extends Seeds{

    public VegetableSeeds(
          String name,
          float price,
          int weight,
          boolean edible) {
            super(name, price, weight, edible);
    }

    @Override
    public float calculateSellingPrice() {
        return getPrice();
    }

}
