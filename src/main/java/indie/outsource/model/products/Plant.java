package indie.outsource.model.products;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
//@BsonDiscriminator(key = "_clazz")
public abstract class Plant extends Product{
    @BsonProperty("growthStage")
    private int growthStage;

    @BsonCreator
    public Plant(
            @BsonProperty("_id") UUID id,
            @BsonProperty("name") String name,
            @BsonProperty("price") float price,
            @BsonProperty("growthStage") int growthStage) {
        super(id,name, price);
        this.growthStage = growthStage;
    }

}
