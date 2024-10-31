package indie.outsource.model.products;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@Getter
@Setter
@NoArgsConstructor
@BsonDiscriminator(key = "_clazz")
abstract public class Plant extends Product{
    @BsonProperty("growthStage")
    private int growthStage;

    @BsonCreator
    public Plant(
//            @BsonProperty("_id") ObjectId id,
            @BsonProperty("name") String name,
            @BsonProperty("price") float price,
            @BsonProperty("growthStage") int growthStage) {
        super(name, price);
        this.growthStage = growthStage;
    }

}
