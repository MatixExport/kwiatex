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
public class Flower extends Plant{
    private String color;

    public Flower(
          String name,
           float price,
           int growthStage,
           String color) {
        super(name, price, growthStage);
        this.color = color;
    }
    @Override
    public float calculateSellingPrice() {
        return getPrice();
    }

    @Override
    public String getProductInfo() {
        return String.format("%s %s within %d growth stage.",getColor(), getName(), getGrowthStage());
    }


}
