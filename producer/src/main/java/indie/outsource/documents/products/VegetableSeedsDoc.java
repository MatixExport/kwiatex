package indie.outsource.documents.products;

import indie.outsource.model.products.GrassesSeeds;
import indie.outsource.model.products.Product;
import indie.outsource.model.products.VegetableSeeds;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Getter
@Setter
@NoArgsConstructor
@BsonDiscriminator(key = "_clazz", value = "vegetableSeeds")
public class VegetableSeedsDoc extends SeedsDoc {

    @BsonCreator
    public VegetableSeedsDoc(
            @BsonProperty("name") String name,
            @BsonProperty("price") float price,
            @BsonProperty("weight") int weight,
            @BsonProperty("edible") boolean edible) {
            super(name, price, weight, edible);
    }

    public VegetableSeedsDoc(VegetableSeeds vegetableSeeds) {
        this.setName(vegetableSeeds.getName());
        this.setPrice(vegetableSeeds.getPrice());
        this.setWeight(vegetableSeeds.getWeight());
        this.setEdible(vegetableSeeds.isEdible());
    }

    public Product toDomainModel(){
        return new GrassesSeeds(
                this.getName(),
                this.getPrice(),
                this.getWeight(),
                this.isEdible()
        );
    }


}
