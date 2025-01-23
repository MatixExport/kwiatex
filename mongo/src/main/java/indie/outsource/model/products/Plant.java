package indie.outsource.model.products;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Getter
@Setter
@NoArgsConstructor
public abstract class Plant extends Product{
    private int growthStage;

    public Plant(
          String name,
          float price,
          int growthStage) {
        super(name, price);
        this.growthStage = growthStage;
    }

}
