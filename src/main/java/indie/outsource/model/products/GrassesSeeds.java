package indie.outsource.model.products;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.*;
import org.bson.types.ObjectId;

@Getter
@Setter
@NoArgsConstructor
//@BsonDiscriminator(key = "_clazz", value = "grassesSeeds")
@BsonDiscriminator("GrassesSeeds")
public class GrassesSeeds extends Seeds{

    @BsonCreator
    public GrassesSeeds(
//           @BsonProperty("_id") ObjectId id,
           @BsonProperty("name") String name,
           @BsonProperty("price") float price,
           @BsonProperty("weight") int weight,
           @BsonProperty("edible") boolean edible) {
        super(name,price ,weight, edible);
    }

    @BsonIgnore
    @Override
    public float calculateSellingPrice() {
        return getWeight() * getPrice();
    }


}
