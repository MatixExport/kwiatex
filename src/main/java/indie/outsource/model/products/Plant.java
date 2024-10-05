package indie.outsource.model.products;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public abstract class Plant extends Product{

    private int growthStage;

    public Plant(int id, String name, float price, int growthStage) {
        super(id, name, price);
        this.growthStage = growthStage;
    }

}
