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
//@BsonDiscriminator(key = "_clazz", value = "flower")
@BsonDiscriminator("Glower")
public class Flower extends Plant{
    @BsonProperty("color")
    private String color;

    @BsonCreator
    public Flower(
//           @BsonProperty("_id") ObjectId id,
           @BsonProperty("name") String name,
           @BsonProperty("price") float price,
           @BsonProperty("growthStage") int growthStage,
           @BsonProperty("color") String color) {
        super(name, price, growthStage);
        this.color = color;
    }
    @BsonIgnore
    @Override
    public float calculateSellingPrice() {
        return getPrice();
    }

    @BsonIgnore
    @Override
    public String getProductInfo() {
        return String.format("%s %s within %d growth stage.",getColor(), getName(), getGrowthStage());
    }


}
