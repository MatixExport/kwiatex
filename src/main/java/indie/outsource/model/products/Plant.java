package indie.outsource.model.products;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Access(AccessType.FIELD)
public abstract class Plant extends Product{

    private int growthStage;

    public Plant(int id, String name, float price, int growthStage) {
        super(id, name, price);
        this.growthStage = growthStage;
    }

}
