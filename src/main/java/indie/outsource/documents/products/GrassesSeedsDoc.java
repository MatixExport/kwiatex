package indie.outsource.documents.products;

import indie.outsource.model.products.GrassesSeeds;
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
@BsonDiscriminator(key = "_clazz", value = "grassesSeeds")
public class GrassesSeedsDoc extends SeedsDoc {

    @BsonCreator
    public GrassesSeedsDoc(
           @BsonProperty("name") String name,
           @BsonProperty("price") float price,
           @BsonProperty("weight") int weight,
           @BsonProperty("edible") boolean edible) {
           setName(name);
           setPrice(price);
           setWeight(weight);
           setEdible(edible);
    }

    public GrassesSeedsDoc(GrassesSeeds grassesSeeds) {
        this.setName(grassesSeeds.getName());
        this.setPrice(grassesSeeds.getPrice());
        this.setWeight(grassesSeeds.getWeight());
        this.setEdible(grassesSeeds.isEdible());
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
