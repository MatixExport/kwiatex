package indie.outsource.model.products;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Tree extends Plant{
    private int height;

    public Tree(
          String name,
           float price,
           int growthStage,
           int height) {
        super(name,price,growthStage);
        this.height = height;

    }

    @Override
    public float calculateSellingPrice() {
        return getPrice();
    }

    @Override
    public String getProductInfo() {
        return "Sapling of " + getName() + " in " + getGrowthStage() + " growth stage.";
    }
}
