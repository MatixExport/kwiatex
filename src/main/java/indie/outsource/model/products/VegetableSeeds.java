package indie.outsource.model.products;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@Getter
@Setter
@NoArgsConstructor
@BsonDiscriminator(key = "_clazz", value = "vegetableSeeds")
public class VegetableSeeds extends Seeds{

    @BsonCreator
    public VegetableSeeds(
//            @BsonProperty("_id") ObjectId id,
            @BsonProperty("name") String name,
            @BsonProperty("price") float price,
            @BsonProperty("weight") int weight,
            @BsonProperty("edible") boolean edible) {
        super(name, price ,weight, edible);
    }

    @BsonIgnore
    @Override
    public float calculateSellingPrice() {
        return getPrice();
    }

}
