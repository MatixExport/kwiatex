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

    public Plant( String name, float price, int growthStage) {
        super(name, price);
        this.growthStage = growthStage;
    }

}
