package indie.outsource.model.products;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
//@BsonDiscriminator(key = "_clazz", value = "vegetableSeeds")
public class VegetableSeeds extends Seeds{

    @BsonCreator
    public VegetableSeeds(
            @BsonProperty("_id") UUID id,
            @BsonProperty("name") String name,
            @BsonProperty("price") float price,
            @BsonProperty("weight") int weight,
            @BsonProperty("edible") boolean edible) {
        super( id,name, price, weight, edible);
    }

    @BsonIgnore
    @Override
    public float calculateSellingPrice() {
        return getPrice();
    }

}
