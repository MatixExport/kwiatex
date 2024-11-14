package indie.outsource.documents.products;

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
@BsonDiscriminator(key = "_clazz")
abstract public class SeedsDoc extends ProductDoc {

    @BsonProperty("weight")
    private int weight;
    @BsonProperty("edible")
    private boolean edible;

    @BsonCreator
    public SeedsDoc(
            @BsonProperty("name") String name,
            @BsonProperty("price") float price,
            @BsonProperty("weight") int weight,
            @BsonProperty("edible") boolean edible) {
        super(name,price);
        this.weight = weight;
        this.edible = edible;
    }
}
