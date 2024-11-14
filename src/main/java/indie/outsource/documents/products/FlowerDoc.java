package indie.outsource.documents.products;

import indie.outsource.model.products.Flower;
import indie.outsource.model.products.Product;
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
@BsonDiscriminator(key = "_clazz", value = "flower")
public class FlowerDoc extends PlantDoc {
    @BsonProperty("color")
    private String color;

    @BsonCreator
    public FlowerDoc(
           @BsonProperty("name") String name,
           @BsonProperty("price") float price,
           @BsonProperty("growthStage") int growthStage,
           @BsonProperty("color") String color) {
        super(name, price, growthStage);
        this.color = color;
    }

    public Product toDomainModel(){
        return new Flower(
                this.getName(),
                this.getPrice(),
                this.getGrowthStage(),
                this.getColor()
        );
    }


}
