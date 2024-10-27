package indie.outsource.model.products;

import jakarta.persistence.*;
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
@Access(AccessType.FIELD)
@NoArgsConstructor
//@BsonDiscriminator(key = "_clazz")
public abstract class Seeds extends Product {

    @BsonProperty("weight")
    private int weight;
    @BsonProperty("edible")
    private boolean edible;

    @BsonCreator
    public Seeds(
            @BsonProperty("_id") UUID id,
            @BsonProperty("name") String name,
            @BsonProperty("price") float price,
            @BsonProperty("weight") int weight,
            @BsonProperty("edible") boolean edible) {
        super(id ,name, price);
        this.weight = weight;
        this.edible = edible;
    }
    @BsonIgnore
    @Override
    public String getProductInfo() {
        return getWeight()+ " bag of " + getName() + " seeds";
    }
}
