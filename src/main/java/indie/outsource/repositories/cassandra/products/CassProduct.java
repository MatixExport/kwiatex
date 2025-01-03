package indie.outsource.repositories.cassandra.products;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.NamingStrategy;
import com.datastax.oss.driver.api.mapper.entity.naming.NamingConvention;
import indie.outsource.model.ProductWithInfo;
import indie.outsource.model.products.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@NamingStrategy(convention = NamingConvention.SNAKE_CASE_INSENSITIVE)
public class CassProduct {

    private Integer quantity;
    @CqlName("product_id")
    private Integer productId;
    private String name;
    private Float price;
    @CqlName("growthStage")
    private Integer growthStage;
    private String color;
    private Integer weight;
    private Boolean edible;
    private Integer height;
    private String discriminator;


    public CassProduct(ProductWithInfo productWithInfo) {
        this.quantity = productWithInfo.getQuantity();
        Product product = productWithInfo.getProduct();
        this.productId = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();

        switch (product.getClass().getSimpleName()){
            case "Flower":
                Flower flower = (Flower) product;
                this.growthStage = flower.getGrowthStage();
                this.color = flower.getColor();
                setDiscriminator(Flower.class.getSimpleName());
                break;
            case "GrassesSeeds":
                GrassesSeeds grasses = (GrassesSeeds) product;
                this.weight = grasses.getWeight();
                this.edible = grasses.isEdible();
                setDiscriminator(GrassesSeeds.class.getSimpleName());
                break;
            case "Tree":
                Tree tree = (Tree) product;
                this.height = tree.getHeight();
                setGrowthStage(tree.getGrowthStage());
                setDiscriminator(Tree.class.getSimpleName());
                break;
            case "VegetableSeeds":
                VegetableSeeds vegetable = (VegetableSeeds) product;
                this.weight = vegetable.getWeight();
                this.edible = vegetable.isEdible();
                setDiscriminator(VegetableSeeds.class.getSimpleName());
                break;
            default:
                throw new RuntimeException("Unsupported class: " + product.getClass().getSimpleName());
        }
    }

    public ProductWithInfo toDomainModel(){
        ProductWithInfo productWithInfo = new ProductWithInfo();
        productWithInfo.setQuantity(quantity);
        switch (getDiscriminator()){
            case "Flower":
                productWithInfo.setProduct(
                        new Flower(
                            productId,name,price,growthStage,color
                        )
                );
                break;
            case "GrassesSeeds":
                productWithInfo.setProduct(
                        new GrassesSeeds(
                                productId,name,price,weight,edible
                        )
                );
                break;
            case "Tree":
                productWithInfo.setProduct(
                        new Tree(
                                productId,name,price,growthStage,height
                        )
                );
                break;
            case "VegetableSeeds":
                productWithInfo.setProduct(
                        new VegetableSeeds(
                                productId,name,price,weight,edible
                        )
                );
                break;
            default:
                throw new RuntimeException("Invalid discriminator: " + getDiscriminator());
        }
        return productWithInfo;
    }

}
