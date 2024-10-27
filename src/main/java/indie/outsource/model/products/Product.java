package indie.outsource.model.products;


import indie.outsource.model.AbstractEntity;
import indie.outsource.model.ProductInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Access(AccessType.FIELD)
//@BsonDiscriminator(key = "_clazz")
public abstract class Product extends AbstractEntity implements Serializable {

    @BsonProperty("name")
    private String name;
    @BsonProperty("price")
    private float price;

    @BsonIgnore
    public abstract float calculateSellingPrice();
    @BsonIgnore
    public abstract String getProductInfo();


    public Product(
            String name,
            float price) {
        this.name = name;
        this.price = price;
    }

    @BsonCreator
    public Product(
            @BsonProperty("_id") UUID id,
            @BsonProperty("name") String name,
            @BsonProperty("price") float price) {
        super(id);
        this.name = name;
        this.price = price;
    }

    @BsonIgnore
    public ProductInfo getProductWithInfo() {
        return null;
    }
}
