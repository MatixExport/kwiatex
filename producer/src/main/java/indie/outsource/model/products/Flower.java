package indie.outsource.model.products;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Flower extends Plant{
    private String color;

    public Flower(
          String name,
           float price,
           int growthStage,
           String color) {
        super(name, price, growthStage);
        this.color = color;
    }
    @Override
    public float calculateSellingPrice() {
        return getPrice();
    }

    @Override
    public String getProductInfo() {
        return String.format("%s %s within %d growth stage.",getColor(), getName(), getGrowthStage());
    }


}
