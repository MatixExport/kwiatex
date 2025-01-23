package indie.outsource.documents.products;

import indie.outsource.model.products.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Getter
@Setter
@NoArgsConstructor
@BsonDiscriminator(key = "_clazz")
abstract public class PlantDoc extends ProductDoc {
    @BsonProperty("growthStage")
    private int growthStage;

    @BsonCreator
    public PlantDoc(
            @BsonProperty("name") String name,
            @BsonProperty("price") float price,
            @BsonProperty("growthStage") int growthStage) {
        super(name, price);
        this.growthStage = growthStage;
    }


}
