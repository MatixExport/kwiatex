package indie.outsource.documents.products;


import indie.outsource.model.AbstractEntity;
import indie.outsource.model.products.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;


@Setter
@Getter
@NoArgsConstructor
@BsonDiscriminator(key = "_clazz")
public class ProductDoc extends AbstractEntity {

    @BsonProperty("name")
    private String name ;

    @BsonProperty("price")
    private float price;


    @BsonCreator
    public ProductDoc(
            @BsonProperty("name") String name,
            @BsonProperty("price") float price) {
        this.name = name;
        this.price = price;
    }

    public ProductDoc(Product product){
        this.name = product.getName();
        this.price = product.getPrice();
    }

    public Product toDomainModel(){
        return null;
    }
}
