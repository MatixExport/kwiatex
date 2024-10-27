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
@BsonDiscriminator(key = "_clazz",value = "tree")
public class Tree extends Plant{
    @BsonProperty("height")
    private int height;

    @BsonCreator
    public Tree(
//            @BsonProperty("_id") ObjectId id,
            @BsonProperty("name") String name,
            @BsonProperty("price") float price,
            @BsonProperty("growthStage") int growthStage,
            @BsonProperty("height") int height) {
        super(name,price ,growthStage);
        this.height = height;
    }



    @BsonIgnore
    @Override
    public float calculateSellingPrice() {
        return getPrice();
    }

    @BsonIgnore
    @Override
    public String getProductInfo() {
        return "Sapling of " + getName() + " in " + getGrowthStage() + " growth stage.";
    }
}
