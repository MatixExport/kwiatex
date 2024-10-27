package indie.outsource.model.products;


import indie.outsource.model.AbstractEntity;
import indie.outsource.model.ProductInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@BsonDiscriminator(key = "_clazz",value = "product")
public class Product implements Serializable {

    @BsonProperty("name")
    private String name = "";
    @BsonProperty("price")
    private float price;

    @BsonIgnore
    public  float calculateSellingPrice(){
        return 0;
    };
    @BsonIgnore
    public  String getProductInfo(){
        return "";
    };



    @BsonCreator
    public Product(
//            @BsonProperty("_id") ObjectId id,
            @BsonProperty("name") String name,
            @BsonProperty("price") float price) {
//        super(id);
        this.name = name;
        this.price = price;
    }

    public float getPrice(){
        return price;
    }
    public void setPrice(float price){
        this.price = price;
    }


    @BsonIgnore
    public ProductInfo getProductWithInfo() {
        return null;
    }
}
