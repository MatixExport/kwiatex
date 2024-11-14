package indie.outsource.documents.products;

import indie.outsource.model.products.Flower;
import indie.outsource.model.products.Product;
import indie.outsource.model.products.Tree;
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
@BsonDiscriminator(key = "_clazz",value = "tree")
public class TreeDoc extends PlantDoc {
    @BsonProperty("height")
    private int height;

    @BsonCreator
    public TreeDoc(
            @BsonProperty("name") String name,
            @BsonProperty("price") float price,
            @BsonProperty("growthStage") int growthStage,
            @BsonProperty("height") int height) {
        super(name,price,growthStage);
        this.height = height;

    }
    public Product toDomainModel(){
        return new Tree(
                this.getName(),
                this.getPrice(),
                this.getGrowthStage(),
                this.getHeight()
        );
    }

    public TreeDoc(Tree tree){
        this.setName(tree.getName());
        this.setPrice(tree.getPrice());
        this.setGrowthStage(tree.getGrowthStage());
        this.setHeight(tree.getHeight());
    }

}
