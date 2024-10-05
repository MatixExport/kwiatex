package indie.outsource.model.products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Tree extends Plant{
    private int height;

    public Tree(int id, String name, float price, int growthStage, int height) {
        super(id, name, price, growthStage);
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
