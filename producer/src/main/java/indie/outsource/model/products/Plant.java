package indie.outsource.model.products;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class Plant extends Product{
    private int growthStage;

    public Plant(
          String name,
          float price,
          int growthStage) {
        super(name, price);
        this.growthStage = growthStage;
    }

}
